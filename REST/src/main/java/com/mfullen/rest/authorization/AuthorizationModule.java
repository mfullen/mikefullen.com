package com.mfullen.rest.authorization;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ResourceFilter;

/**
 *
 * @author mfullen
 */
public class AuthorizationModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(ContainerRequestFilter.class).to(SecurityContextFilter.class);//.in(Scopes.SINGLETON);
        bind(ResourceFilter.class).to(SecurityContextFilter.class);//.in(Scopes.SINGLETON);
        bind(com.sun.jersey.spi.container.ResourceFilterFactory.class).to(ResourceFilterFactory.class).in(Scopes.SINGLETON);
    }
}
