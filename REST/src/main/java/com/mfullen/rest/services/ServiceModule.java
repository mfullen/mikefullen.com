package com.mfullen.rest.services;

import com.google.inject.AbstractModule;

/**
 *
 * @author mfullen
 */
public class ServiceModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(UserService.class).to(UserServiceImpl.class);
        bind(PasswordEncryptionService.class).to(PasswordEncryptionServiceImpl.class);
    }
}
