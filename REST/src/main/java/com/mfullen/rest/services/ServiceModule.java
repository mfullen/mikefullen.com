package com.mfullen.rest.services;

import com.google.inject.AbstractModule;
import com.mfullen.rest.services.account.AccountModule;
import com.mfullen.rest.services.encryption.EncryptionModule;
import com.mfullen.rest.services.verification.VerificationModule;

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
        install(new AccountModule());
        install(new EncryptionModule());
        install(new VerificationModule());
    }
}
