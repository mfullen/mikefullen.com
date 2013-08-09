package com.mfullen.rest.services;

import com.mfullen.model.Role;
import com.mfullen.rest.model.AuthenticatedUserToken;
import com.mfullen.rest.model.request.CreateUserRequest;
import com.mfullen.rest.model.request.LoginRequest;

/**
 *
 * @author mfullen
 */
public interface UserService
{
    AuthenticatedUserToken login(LoginRequest request);

    AuthenticatedUserToken register(CreateUserRequest request, Role role);
}
