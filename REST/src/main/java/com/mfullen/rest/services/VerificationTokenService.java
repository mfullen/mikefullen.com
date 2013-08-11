package com.mfullen.rest.services;

import com.mfullen.model.VerificationToken;
import com.mfullen.model.VerificationTokenType;

/**
 *
 * @author mfullen
 */
public interface VerificationTokenService
{
    VerificationToken getActiveLostPasswordToken(String username);

    VerificationToken getActiveEmailVerificationToken(String username);

    VerificationToken getActiveEmailRegistrationToken(String username);

    VerificationToken getActiveToken(String username, VerificationTokenType tokenType);

    VerificationToken sendEmailVerificationToken(String userId);

    VerificationToken sendEmailRegistrationToken(String userId);

    //VerificationToken sendLostPasswordToken(LostPasswordRequest lostPasswordRequest);
    VerificationToken verify(String base64EncodedToken);

    VerificationToken generateEmailVerificationToken(String emailAddress);
    //VerificationToken resetPassword(String base64EncodedToken, PasswordRequest passwordRequest);
}
