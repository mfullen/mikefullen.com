package com.mfullen.rest;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ResourceFilter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mfullen
 */
public abstract class JerseyModule extends GuiceServletContextListener
{
    private static final ThreadLocal<Injector> threadLocalInj = new ThreadLocal<>();
    public static final String CONFIG_PROPERTY_PACKAGES = "com.sun.jersey.config.property.packages";
    public static final String POJO_MAPPING_FEATURE = "com.sun.jersey.api.json.POJOMappingFeature";

    static Injector injector()
    {
        return threadLocalInj.get();
    }
    private static final Logger logger = LoggerFactory.getLogger(JerseyModule.class.getName());

    protected abstract void configure(Binder binder);

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent)
    {
        super.contextDestroyed(servletContextEvent);
        threadLocalInj.remove();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent)
    {
        if (threadLocalInj.get() != null)
        {
            logger.warn("Injector already exists. ServletContextListener.contextDestroyed was not invoked?");
        }
        super.contextInitialized(servletContextEvent);
        Injector in = (Injector) servletContextEvent.getServletContext()
                .getAttribute(Injector.class.getName());
        if (in == null)
        {
            logger.warn("Injector is not set");
        }
        threadLocalInj.set(in);
    }

    @Override
    protected Injector getInjector()
    {
        Injector createInjector = Guice.createInjector(makeJerseyServletModule());
        return createInjector;
    }

    protected JerseyServletModule makeJerseyServletModule()
    {
        return new JerseyServletModule()
        {
            @Override
            protected void configureServlets()
            {
                JerseyModule.this.configure(binder());
                bind(JacksonJsonProvider.class).in(Scopes.SINGLETON);
                bind(ContainerRequestFilter.class).to(SecurityContextFilter.class).in(Scopes.SINGLETON);
                bind(ResourceFilter.class).to(SecurityContextFilter.class).in(Scopes.SINGLETON);
                bind(com.sun.jersey.spi.container.ResourceFilterFactory.class).to(ResourceFilterFactory.class).in(Scopes.SINGLETON);
                final Map<String, String> params = new HashMap<>();
                params.put(POJO_MAPPING_FEATURE, "true");
                //params.put(CONFIG_PROPERTY_PACKAGES, REST_RESOURCES);

                params.put(ResourceConfig.PROPERTY_RESOURCE_FILTER_FACTORIES,
                        ResourceFilterFactory.class.getCanonicalName());
                serve("/*").with(GuiceContainer.class, params);
            }
        };
    }
}