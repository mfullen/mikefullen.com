package com.mfullen.rest.services.account;

import com.google.inject.AbstractModule;

/**
 *
 * @author mfullen
 */
public class AccountModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(UserService.class).to(UserServiceImpl.class);
    }
}
