/*
 * Copyright PWF Technology LLC
 */
package com.mfullen.rest.services;

import static org.junit.Assert.*;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.mfullen.rest.PersistenceInit;
import com.mfullen.rest.ResourceFilterFactory;
import com.mfullen.rest.RestApplicationServletModule;
import com.mfullen.rest.model.AuthenticatedUserToken;
import com.mfullen.rest.model.request.CreateUserRequest;
import com.mfullen.rest.model.request.LoginRequest;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author mfullen
 */
public class UserServiceImplTest
{
    private UserService userService;

    @Before
    public void setup()
    {
        Injector injector = Guice.createInjector(new JpaPersistModule("test"), new RestApplicationServletModule());
        injector.getInstance(PersistenceInit.class);
        this.userService = injector.getInstance(UserService.class);
        injector.getInstance(ResourceFilterFactory.class);
    }

    @Test
    public void testLogin()
    {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@test.com");
        request.setUsername("batman");
        request.setPassword("hunter2");
        AuthenticatedUserToken register = this.userService.register(request);
        assertEquals(1, register.getUserId(), 0);

        LoginRequest loginRequest = new LoginRequest("batman", "hunter2");
        AuthenticatedUserToken login = this.userService.login(loginRequest);
        assertEquals(1, login.getUserId(), 0);
    }

    @Test
    public void testRegister()
    {

    }

    @Test
    public void testCreateNewUser()
    {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@test.com");
        request.setUsername("batman");
        request.setPassword("hunter2");
        AuthenticatedUserToken register = this.userService.register(request);
        assertEquals(1, register.getUserId(), 0);

        // assertFalse("hunter2".equals(request));
    }
}
