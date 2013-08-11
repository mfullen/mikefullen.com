package com.mfullen.rest;

import com.google.inject.Scopes;
import com.google.inject.servlet.GuiceFilter;
import com.mfullen.rest.authorization.ResourceFilterFactory;
import com.mfullen.rest.authorization.SecurityContextFilter;
import com.mfullen.rest.resources.ResourceModule;
import com.mfullen.rest.services.ServiceModule;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ResourceFilter;
import java.util.HashMap;
import java.util.Map;
import org.apache.bval.guice.ValidationModule;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

/**
 *
 * @author mfullen
 */
public class RestApplicationServletModule extends JerseyServletModule
{
    public static final String CONFIG_PROPERTY_PACKAGES = "com.sun.jersey.config.property.packages";
    public static final String POJO_MAPPING_FEATURE = "com.sun.jersey.api.json.POJOMappingFeature";
    public static final String REST_RESOURCES = "com.mfullen.rest.resources";

    @Override
    protected void configureServlets()
    {
        //binder().requireExplicitBindings();
        install(new ServiceModule());
        install(new ResourceModule());
        install(new ValidationModule());

        bind(GuiceContainer.class);
        bind(GuiceFilter.class);
        bind(ContainerRequestFilter.class).to(SecurityContextFilter.class).in(Scopes.SINGLETON);
        bind(ResourceFilter.class).to(SecurityContextFilter.class).in(Scopes.SINGLETON);
        bind(com.sun.jersey.spi.container.ResourceFilterFactory.class).to(ResourceFilterFactory.class).in(Scopes.SINGLETON);


        bind(JacksonJsonProvider.class).in(Scopes.SINGLETON);

        final Map<String, String> params = new HashMap<>();
        params.put(POJO_MAPPING_FEATURE, "true");
        params.put(CONFIG_PROPERTY_PACKAGES, REST_RESOURCES);

        params.put(ResourceConfig.PROPERTY_RESOURCE_FILTER_FACTORIES,
                ResourceFilterFactory.class.getCanonicalName());



        serve("/rest/*").with(GuiceContainer.class, params);
    }
}
