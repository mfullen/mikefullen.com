package com.mfullen.rest;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 *
 * @author mfullen
 */
public class MyGuiceServletConfig extends GuiceServletContextListener
{
    @Override
    public Injector getInjector()
    {
        return Guice.createInjector(new RestApplicationServletModule());
    }
}
