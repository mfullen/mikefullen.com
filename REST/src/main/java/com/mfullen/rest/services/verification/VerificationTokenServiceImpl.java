package com.mfullen.rest.services.verification;

import com.google.common.base.Preconditions;
import com.mfullen.model.UserModel;
import com.mfullen.model.VerificationToken;
import com.mfullen.model.VerificationTokenType;
import com.mfullen.repositories.UserRepository;
import com.mfullen.repositories.VerificationTokenRepository;
import com.mfullen.rest.exceptions.AlreadyVerifiedException;
import com.mfullen.rest.exceptions.TokenHasExpiredException;
import com.mfullen.rest.exceptions.TokenNotFoundException;
import com.mfullen.rest.exceptions.UserNotFoundException;
import com.mfullen.rest.services.HostnameUrl;
import com.mfullen.rest.services.email.EmailGatewayService;
import com.mfullen.rest.services.email.EmailServiceTokenModel;
import javax.inject.Inject;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author mfullen
 */
class VerificationTokenServiceImpl implements VerificationTokenService
{
    public static final int EmailVerificationTokenExpiryTimeInMinutes = 24;
    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final EmailGatewayService emailGatewayService;
    private final String hostnameUrl;

    @Inject
    public VerificationTokenServiceImpl(UserRepository userRepository,
                                        VerificationTokenRepository tokenRepository,
                                        EmailGatewayService emailGatewayService,
                                        @HostnameUrl String hostnameUrl)
    {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailGatewayService = emailGatewayService;
        this.hostnameUrl = hostnameUrl;
    }

    @Override
    public VerificationToken getActiveLostPasswordToken(String username)
    {
        return getActiveToken(username, VerificationTokenType.lostPassword);
    }

    @Override
    public VerificationToken getActiveEmailVerificationToken(String username)
    {
        return getActiveToken(username, VerificationTokenType.emailVerification);
    }

    @Override
    public VerificationToken getActiveEmailRegistrationToken(String username)
    {
        return getActiveToken(username, VerificationTokenType.emailRegistration);
    }

    @Override
    public VerificationToken getActiveToken(String username, VerificationTokenType tokenType)
    {
        UserModel user = this.userRepository.findByUserName(username);

        if (user == null)
        {
            throw new UserNotFoundException();
        }

        return getActiveToken(user, tokenType);
    }

    public VerificationToken getActiveToken(UserModel user, VerificationTokenType tokenType)
    {
        VerificationToken activeToken = null;
        for (VerificationToken token : user.getVerificationTokens())
        {
            if (token.getTokenType().equals(tokenType)
                    && !token.hasExpired() && !token.isVerified())
            {
                activeToken = token;
                break;
            }
        }
        return activeToken;
    }

    @Override
    public VerificationToken sendEmailVerificationToken(String username)
    {
        UserModel user = getUser(username);
        return sendEmailVerificationToken(user);
    }

    @Override
    public VerificationToken sendEmailRegistrationToken(String username)
    {
        UserModel user = getUser(username);
        VerificationToken token = new VerificationToken(user,
                VerificationTokenType.emailRegistration,
                EmailVerificationTokenExpiryTimeInMinutes);
        user.addVerificationToken(token);
        userRepository.save(user);
        emailGatewayService.sendVerificationToken(new EmailServiceTokenModel(user,
                token, hostnameUrl));
        return token;
    }

    @Override
    public VerificationToken verify(String base64EncodedToken)
    {
        VerificationToken token = loadToken(base64EncodedToken);
        if (token.isVerified() || token.getUser().isVerified())
        {
            throw new AlreadyVerifiedException();
        }
        token.setVerified(true);
        token.getUser().setVerified(true);
        userRepository.save(token.getUser());
        return token;
    }

    @Override
    public VerificationToken generateEmailVerificationToken(String emailAddress)
    {
        Preconditions.checkNotNull(emailAddress);
        UserModel user = userRepository.findByEmail(emailAddress);
        if (user == null)
        {
            throw new UserNotFoundException();
        }
        if (user.isVerified())
        {
            throw new AlreadyVerifiedException();
        }
        //if token still active resend that
        VerificationToken token = this.getActiveToken(user, VerificationTokenType.emailVerification);
        if (token == null)
        {
            token = sendEmailVerificationToken(user);
        }
        else
        {
            emailGatewayService.sendVerificationToken(new EmailServiceTokenModel(user, token, hostnameUrl));
        }
        return token;
    }

    protected VerificationToken loadToken(String base64EncodedToken)
    {
        if (base64EncodedToken == null)
        {
            throw new IllegalArgumentException("Token cannot be null");
        }

        String rawToken = new String(Base64.decodeBase64(base64EncodedToken));
        VerificationToken token = tokenRepository.findByToken(rawToken);
        if (token == null)
        {
            throw new TokenNotFoundException();
        }
        if (token.hasExpired())
        {
            throw new TokenHasExpiredException();
        }
        return token;
    }

    protected UserModel getUser(String username)
    {
        Preconditions.checkNotNull(username);

        UserModel user = userRepository.findByUserName(username);

        if (user == null)
        {
            throw new UserNotFoundException();
        }
        return user;
    }

    protected VerificationToken sendEmailVerificationToken(UserModel user)
    {
        VerificationToken token = new VerificationToken(user, VerificationTokenType.emailVerification,
                EmailVerificationTokenExpiryTimeInMinutes);
        user.addVerificationToken(token);
        userRepository.save(user);
        emailGatewayService.sendVerificationToken(new EmailServiceTokenModel(user, token, hostnameUrl));
        return token;
    }
}
