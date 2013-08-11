package com.mfullen.rest.services;

import com.mfullen.model.Role;
import com.mfullen.rest.request.CreateUserRequest;
import com.mfullen.rest.request.LoginRequest;
import com.mfullen.rest.security.AuthenticatedUserToken;

/**
 *
 * @author mfullen
 */
public interface UserService
{
    AuthenticatedUserToken login(LoginRequest request);

    AuthenticatedUserToken register(CreateUserRequest request, Role role);
}
