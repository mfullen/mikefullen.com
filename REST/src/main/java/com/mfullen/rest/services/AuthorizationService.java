package com.mfullen.rest.services;

import com.mfullen.rest.AuthorizationRequestContext;
import com.mfullen.rest.PrincipalUser;

/**
 *
 * @author mfullen
 */
public interface AuthorizationService
{
    /**
     * Given an AuthorizationRequestContext validate and authorize a User
     *
     * @param authorizationRequestContext the context required to authorize a
     * user for a particular request
     * @return ExternalUser
     */
    public PrincipalUser authorize(AuthorizationRequestContext authorizationRequestContext);
}
