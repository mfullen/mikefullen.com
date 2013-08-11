package com.mfullen.rest.services.verification;

import com.google.inject.AbstractModule;

/**
 *
 * @author mfullen
 */
public class VerificationModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(VerificationTokenService.class).to(VerificationTokenServiceImpl.class);
    }
}
