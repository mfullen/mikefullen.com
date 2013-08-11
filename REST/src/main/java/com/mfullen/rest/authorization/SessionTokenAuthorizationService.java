package com.mfullen.rest.authorization;

import com.mfullen.model.UserModel;
import com.mfullen.repositories.UserRepository;

/**
 *
 * Simple authorization service that requires a session token in the
 * Authorization header This is then matched to a user
 *
 */
public class SessionTokenAuthorizationService implements AuthorizationService
{
    /**
     * directly access user objects
     */
    private final UserRepository userRepository;

    public SessionTokenAuthorizationService(UserRepository repository)
    {
        this.userRepository = repository;
    }

    @Override
    public PrincipalUser authorize(AuthorizationRequestContext securityContext)
    {
        String token = securityContext.getAuthorizationToken();
        PrincipalUser externalUser = null;
        if (token == null)
        {
            return externalUser;
        }
        final UserModel user = userRepository.findByApiKey(token);
        if (user == null)
        {
            throw new NullPointerException("Session token not valid");
        }

        // sessionToken.setLastUpdated(new Date());
        //set last login date.
        userRepository.save(user);
        externalUser = new PrincipalUserImpl(user);


        return externalUser;
    }
}
