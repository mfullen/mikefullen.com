package com.mfullen.rest;

import com.google.inject.Injector;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.spi.container.GuiceComponentProviderFactory;
import com.sun.jersey.spi.container.WebApplication;
import com.sun.jersey.spi.container.WebApplicationFactory;
import com.sun.jersey.test.framework.LowLevelAppDescriptor;
import com.sun.jersey.test.framework.impl.container.inmemory.TestResourceClientHandler;
import com.sun.jersey.test.framework.spi.container.TestContainer;
import java.net.URI;
import java.util.Set;
import javax.ws.rs.core.UriBuilder;

/**
 *
 * @author mfullen
 */
public final class GuiceInMemoryTestContainer implements TestContainer
{
    // Copy other fields from InMemoryTestContainer here.
    final Injector injector;
    final URI baseUri;
    final ResourceConfig resourceConfig;
    final WebApplication webApp;

    /**
     * Creates an instance of {@link InMemoryTestContainer}
     *
     * @param baseUri URI of the application
     * @param ad instance of {@link LowLevelAppDescriptor}
     */
    /**
     * Creates an instance of {@link InMemoryTestContainer}
     *
     * @param baseUri URI of the application
     * @param ad instance of {@link LowLevelAppDescriptor}
     */
    public GuiceInMemoryTestContainer(final URI baseUri, final LowLevelAppDescriptor ad, final Injector injector)
    {
        // Copy other statements from InMemoryTestContainer here
        this.injector = injector;
        this.baseUri = UriBuilder.fromUri(baseUri).build();
        //LOG.info( "Creating low level InMemory test container configured at the base URI " + this.baseUri );
        resourceConfig = ad.getResourceConfig();
       // resourceConfig.getProperties().putAll(BaseResourceTest.params);
        webApp = WebApplicationFactory.createWebApplication();
    }

    // Copy other methods from InMemoryTestContainer here
    @Override
    public void start()
    {
        if (!webApp.isInitiated())
        {
            //LOGGER.info("Starting low level InMemory test container");
            webApp.initiate(resourceConfig, new GuiceComponentProviderFactory(resourceConfig, injector));
        }
    }

    @Override
    public void stop()
    {
        if (webApp.isInitiated())
        {
            //LOG.info( "Stopping low level InMemory test container" );
            webApp.destroy();
        }
    }

    @Override
    public Client getClient()
    {
        ClientConfig clientConfig = null;
        final Set providerSingletons = resourceConfig.getProviderSingletons();
        if (providerSingletons.size() > 0)
        {
            clientConfig = new DefaultClientConfig();
            for (final Object providerSingleton : providerSingletons)
            {
                clientConfig.getSingletons().add(providerSingleton);
            }
        }
        final Client client = clientConfig == null ? new Client(new TestResourceClientHandler(baseUri, webApp)) : new Client(new TestResourceClientHandler(baseUri, webApp), clientConfig);
        return client;
    }

    @Override
    public URI getBaseUri()
    {
        return baseUri;
    }
}
