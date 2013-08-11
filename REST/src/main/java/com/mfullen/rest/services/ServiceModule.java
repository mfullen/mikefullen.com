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
        bind(String.class).annotatedWith(HostnameUrl.class).toInstance("http://localhost:9998/");
        bind(UserService.class).to(UserServiceImpl.class);
        bind(PasswordEncryptionService.class).to(PasswordEncryptionServiceImpl.class);
        bind(VerificationTokenService.class).to(VerificationTokenServiceImpl.class);
    }
}
