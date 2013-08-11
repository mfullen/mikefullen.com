/*
 * Copyright PWF Technology LLC
 */
package com.mfullen.rest.resources;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.google.inject.Module;
import com.mfullen.model.Role;
import com.mfullen.rest.BaseResourceTest;
import com.mfullen.rest.ProviderTestModule;
import com.mfullen.rest.exceptions.AuthenticationException;
import com.mfullen.rest.exceptions.DuplicateUserException;
import com.mfullen.rest.exceptions.UserNotFoundException;
import com.mfullen.rest.exceptions.ValidationException;
import com.mfullen.rest.request.CreateUserRequest;
import com.mfullen.rest.request.LoginRequest;
import com.mfullen.rest.authorization.AuthenticatedUserToken;
import com.mfullen.rest.services.account.UserService;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author mfullen
 */
public class AccountResourceTest extends BaseResourceTest
{
    private UserService userService;

    @Before
    public void setup() throws Exception
    {
        super.setUp();
        AccountResource resource = injector.getInstance(AccountResource.class);
        userService = injector.getInstance(UserService.class);
        resource.setUserServiceProvider(userService);
    }

    @Test
    public void testAuth()
    {
        WebResource webResource = resource();
        ClientResponse response = webResource.path("account").path("test").get(ClientResponse.class);
        assertEquals(Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    @Test
    public void testRegister() throws IOException
    {
        AuthenticatedUserToken authenticatedUserToken = new AuthenticatedUserToken(TEST_USER.getId(), TEST_USER.getApiKey(), TEST_USER.getUserName());
        when(userService.register(any(CreateUserRequest.class), any(Role.class))).thenReturn(authenticatedUserToken);
        when(userService.login(any(LoginRequest.class))).thenReturn(authenticatedUserToken);
        AuthenticatedUserToken token = registerUser();
        assertEquals(TEST_USER.getId(), token.getUserId());
    }

    @Test
    public void testRegisterNullUsername()
    {
        when(userService.register(any(CreateUserRequest.class), any(Role.class))).thenThrow(new ValidationException());
        final CreateUserRequest request = new CreateUserRequest();
        request.setEmail("joker@aol.com");
        request.setPassword("hunter2");
        request.setUsername(null);
        ClientResponse response = resource().path("account").path("register").entity(request, MediaType.APPLICATION_JSON).post(ClientResponse.class);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testRegisterDuplicateUser()
    {
        when(userService.register(any(CreateUserRequest.class), any(Role.class))).thenThrow(new DuplicateUserException());
        final CreateUserRequest request = new CreateUserRequest();
        request.setEmail("joker@aol.com");
        request.setPassword("hunter2");
        request.setUsername("james");
        ClientResponse response = resource().path("account").path("register").entity(request, MediaType.APPLICATION_JSON).post(ClientResponse.class);
        assertEquals(Status.CONFLICT.getStatusCode(), response.getStatus());
    }

    @Test
    public void testSuccessfulLogin()
    {
        AuthenticatedUserToken authenticatedUserToken = new AuthenticatedUserToken(TEST_USER.getId(), TEST_USER.getApiKey(), TEST_USER.getUserName());
        when(userService.register(any(CreateUserRequest.class), any(Role.class))).thenReturn(authenticatedUserToken);
        when(userService.login(any(LoginRequest.class))).thenReturn(authenticatedUserToken);
        ClientResponse response = resource().path("account/login").entity(createLoginRequest(), MediaType.APPLICATION_JSON).post(ClientResponse.class);
        assertEquals(200, response.getStatus());
        Long userId = fromResponse(response).getUserId();
        assertEquals(TEST_USER.getId(), userId);
    }

    @Test
    public void testFailedLogin_NullRequest()
    {
        when(userService.login(any(LoginRequest.class))).thenThrow(new ValidationException());
        ClientResponse response = resource().path("account/login").entity(createLoginRequest(), MediaType.APPLICATION_JSON).post(ClientResponse.class);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testFailedLogin_UserNotFound()
    {
        when(userService.login(any(LoginRequest.class))).thenThrow(new UserNotFoundException());
        ClientResponse response = resource().path("account/login").entity(createLoginRequest(), MediaType.APPLICATION_JSON).post(ClientResponse.class);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testFailedLogin_AuthenticationFailed()
    {
        when(userService.login(any(LoginRequest.class))).thenThrow(new AuthenticationException());
        ClientResponse response = resource().path("account/login").entity(createLoginRequest(), MediaType.APPLICATION_JSON).post(ClientResponse.class);
        assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    public AuthenticatedUserToken registerUser() throws IOException
    {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("joker@aol.com");
        request.setPassword("hunter2");
        request.setUsername("TheJoker");
        WebResource webResource = resource();
        ClientResponse response = webResource.path("account/register").entity(request, MediaType.APPLICATION_JSON).post(ClientResponse.class);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        return fromResponse(response);
    }

    protected AuthenticatedUserToken fromResponse(ClientResponse response)
    {
        return response.getEntity(AuthenticatedUserToken.class);
    }

    @Override
    public List<Class> getClasses()
    {
        final List<Class> classes = new ArrayList<>();
        classes.add(AccountResource.class);
        return classes;
    }

    @Override
    public List<Module> getModules()
    {
        final List<Module> modules = new ArrayList<>();
        modules.add(new ProviderTestModule());
        return modules;
    }
}
