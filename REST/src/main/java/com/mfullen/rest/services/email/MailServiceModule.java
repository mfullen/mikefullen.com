package com.mfullen.rest.services.email;

import com.google.inject.AbstractModule;

/**
 *
 * @author mfullen
 */
public class MailServiceModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(MailService.class).to(MailServiceImpl.class);
    }
}
