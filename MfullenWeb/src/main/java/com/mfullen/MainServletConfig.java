package com.mfullen;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 *
 * @author mfullen
 */
public class MainServletConfig extends GuiceServletContextListener
{
    @Override
    protected Injector getInjector()
    {
        Injector injector = Guice.createInjector(new JpaPersistModule("production"),new MfullenModule());
        //injector.getInstance(AppInitializer.class);
        return injector;
    }
}
