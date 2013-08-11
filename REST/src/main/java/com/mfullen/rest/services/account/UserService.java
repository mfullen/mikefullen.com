package com.mfullen.rest.services.account;

import com.mfullen.model.Role;
import com.mfullen.rest.authorization.AuthenticatedUserToken;
import com.mfullen.rest.request.CreateUserRequest;
import com.mfullen.rest.request.LoginRequest;

/**
 *
 * @author mfullen
 */
public interface UserService
{
    AuthenticatedUserToken login(LoginRequest request);

    AuthenticatedUserToken register(CreateUserRequest request, Role role);
}
