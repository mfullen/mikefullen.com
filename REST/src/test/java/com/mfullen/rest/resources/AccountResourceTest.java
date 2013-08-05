/*
 * Copyright PWF Technology LLC
 */
package com.mfullen.rest.resources;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.mfullen.rest.PersistenceInit;
import com.mfullen.rest.exceptions.ValidationException;
import com.mfullen.rest.model.AuthenticatedUserToken;
import com.mfullen.rest.model.request.CreateUserRequest;
import com.mfullen.rest.model.request.LoginRequest;
import com.mfullen.rest.services.ServiceModule;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import org.apache.bval.guice.ValidationModule;
import org.junit.Before;

/**
 *
 * @author mfullen
 */
public class AccountResourceTest
{
    private AccountResource resource;
    private UriInfo uriInfo;

    @Before
    public void setup()
    {
        uriInfo = mock(UriInfo.class);
        UriBuilder uriBuilder = mock(UriBuilder.class);
        when(uriBuilder.path(anyString())).thenReturn(uriBuilder);
        when(uriInfo.getAbsolutePathBuilder()).thenReturn(uriBuilder);
        Injector injector = Guice.createInjector(new JpaPersistModule("test"), new AbstractModule()
        {
            @Override
            protected void configure()
            {
                install(new ServiceModule());
                install(new ResourceModule());
                install(new ValidationModule());
                bind(UriInfo.class).toInstance(uriInfo);
            }
        });
        injector.getInstance(PersistenceInit.class);

        this.resource = injector.getInstance(AccountResource.class);
    }

    public AccountResourceTest()
    {
    }

    @Test
    public void testRegister()
    {
        AuthenticatedUserToken token = registerUser();
        assertEquals(1, token.getUserId(), 0);
    }

    @Test
    public void testRegisterNullUsername()
    {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("joker@aol.com");
        request.setPassword("hunter2");
        request.setUsername(null);

        doThrow(new ValidationException()).when(spy(this.resource)).register(request);
    }

    public AuthenticatedUserToken registerUser()
    {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("joker@aol.com");
        request.setPassword("hunter2");
        request.setUsername("TheJoker");
        Response register = this.resource.register(request);
        return fromResponse(register);
    }

    AuthenticatedUserToken fromResponse(Response response)
    {
        return (AuthenticatedUserToken) response.getEntity();
    }

    @Test
    public void testLogin()
    {
        AuthenticatedUserToken token = registerUser();
        LoginRequest loginRequest = new LoginRequest("TheJoker", "hunter2");
        Response login = this.resource.login(loginRequest);
        Long userId = fromResponse(login).getUserId();
        assertEquals(1, userId, 0);
    }
}
