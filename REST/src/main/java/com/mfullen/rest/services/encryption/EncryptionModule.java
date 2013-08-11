package com.mfullen.rest.services.encryption;

import com.google.inject.AbstractModule;

/**
 *
 * @author mfullen
 */
public class EncryptionModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(PasswordEncryptionService.class).to(PasswordEncryptionServiceImpl.class);
    }
}
