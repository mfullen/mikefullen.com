package com.mfullen.rest;

import com.google.inject.Injector;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.LowLevelAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainer;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import java.net.URI;

/**
 *
 * @author mfullen
 */
public final class GuiceInMemoryTestContainerFactory implements
        TestContainerFactory
{
    private final Injector injector;

    public GuiceInMemoryTestContainerFactory(final Injector injector)
    {
        this.injector = injector;
    }

    @Override
    public Class<LowLevelAppDescriptor> supports()
    {
        return LowLevelAppDescriptor.class;
    }

    @Override
    public TestContainer create(final URI baseUri, final AppDescriptor ad)
    {
        if (!(ad instanceof LowLevelAppDescriptor))
        {
            throw new IllegalArgumentException("The application descriptor must be an instance of LowLevelAppDescriptor");
        }
        return new GuiceInMemoryTestContainer(baseUri, (LowLevelAppDescriptor) ad, injector);
    }
}
