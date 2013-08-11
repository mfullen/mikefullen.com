package com.mfullen.rest;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.mfullen.rest.authorization.ResourceFilterFactory;
import com.mfullen.rest.authorization.SecurityContextFilter;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ResourceFilter;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.junit.Before;

/**
 *
 * @author mfullen
 */
public abstract class MyGuiceJerseyTest extends JerseyTest
{
    public static Injector injector;

    @Before
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
    }

    public abstract List<Class> getClasses();

    public abstract List<Module> getModules();

    @Override
    protected AppDescriptor configure()
    {
        injector = Guice.createInjector(new MyGuiceJerseyModule(getClasses(), getModules()));
        return new WebAppDescriptor.Builder()
                .contextListenerClass(GuiceTestConfig.class)
                .filterClass(GuiceFilter.class)
                .servletPath("/")
                .build();
    }

    public static class GuiceTestConfig extends GuiceServletContextListener
    {
        @Override
        public Injector getInjector()
        {
            return injector;
        }
    }

    private class MyGuiceJerseyModule extends ServletModule
    {
        final private List<Class> classes;
        final private List<Module> modules;

        public MyGuiceJerseyModule(List<Class> classes, List<Module> modules)
        {
            this.classes = classes;
            this.modules = modules;
        }

        @Override
        protected void configureServlets()
        {
            for (Class c : classes)
            {
                bind(c);
            }

            for (Module module : modules)
            {
                install(module);
            }

            bind(JacksonJsonProvider.class).in(Scopes.SINGLETON);
            bind(ContainerRequestFilter.class).to(SecurityContextFilter.class).in(Scopes.SINGLETON);
            bind(ResourceFilter.class).to(SecurityContextFilter.class).in(Scopes.SINGLETON);
            bind(com.sun.jersey.spi.container.ResourceFilterFactory.class).to(ResourceFilterFactory.class).in(Scopes.SINGLETON);
            final Map<String, String> params = new HashMap<>();
            params.put(JSONConfiguration.FEATURE_POJO_MAPPING, "true");
            //params.put(CONFIG_PROPERTY_PACKAGES, REST_RESOURCES);

            params.put(ResourceConfig.PROPERTY_RESOURCE_FILTER_FACTORIES,
                    ResourceFilterFactory.class.getCanonicalName());
            serve("/*").with(GuiceContainer.class, params);
        }
    }
}
