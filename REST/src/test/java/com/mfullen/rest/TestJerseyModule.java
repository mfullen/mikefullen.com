package com.mfullen.rest;

import com.google.inject.Binder;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.mfullen.rest.resources.ResourceModule;
import org.apache.bval.guice.ValidationModule;

/**
 *
 * @author mfullen
 */
public class TestJerseyModule extends JerseyModule
{
    @Override
    protected void configure(Binder binder)
    {
        //binder.install(new JpaPersistModule("test"));
        binder.install(new ResourceModule());
        binder.install(new ProviderTestModule());
        binder.install(new ValidationModule());
    }
}
