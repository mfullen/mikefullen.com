package com.mfullen.rest;

import com.google.inject.Scopes;
import com.google.inject.persist.PersistFilter;
import com.google.inject.servlet.GuiceFilter;
import com.mfullen.rest.authorization.AuthorizationModule;
import com.mfullen.rest.authorization.ResourceFilterFactory;
import com.mfullen.rest.resources.ResourceModule;
import com.mfullen.rest.services.ServiceModule;
import com.mfullen.rest.services.email.MailServiceModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
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
    public static final String REST_RESOURCES = "com.mfullen.rest.resources";

    @Override
    protected void configureServlets()
    {
        install(new ServiceModule());
        install(new ResourceModule());
        install(new ValidationModule());
        install(new AuthorizationModule());

        bind(GuiceContainer.class);
        bind(GuiceFilter.class);

        bind(JacksonJsonProvider.class).in(Scopes.SINGLETON);

        final Map<String, String> params = new HashMap<>();
        params.put(JSONConfiguration.FEATURE_POJO_MAPPING, "true");
        params.put(PackagesResourceConfig.PROPERTY_PACKAGES, REST_RESOURCES);

        params.put(ResourceConfig.PROPERTY_RESOURCE_FILTER_FACTORIES,
                ResourceFilterFactory.class.getCanonicalName());


        filter("/rest/*").through(PersistFilter.class);
        serve("/rest/*").with(GuiceContainer.class, params);
    }
}
