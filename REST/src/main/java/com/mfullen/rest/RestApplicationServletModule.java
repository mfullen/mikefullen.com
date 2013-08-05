package com.mfullen.rest;

import com.google.inject.Scopes;
import com.mfullen.rest.resources.ResourceModule;
import com.mfullen.rest.services.ServiceModule;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.UriInfo;
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
        install(new ServiceModule());
        install(new ResourceModule());
        install(new ValidationModule());

        bind(GuiceContainer.class);
        bind(SecurityContextFilter.class);
        bind(ResourceFilterFactory.class);


        bind(JacksonJsonProvider.class).in(Scopes.SINGLETON);

        final Map<String, String> params = new HashMap<>();
        params.put(POJO_MAPPING_FEATURE, "true");
        params.put(CONFIG_PROPERTY_PACKAGES, REST_RESOURCES);

        params.put(ResourceConfig.PROPERTY_RESOURCE_FILTER_FACTORIES,
                ResourceFilterFactory.class.getCanonicalName());



        serve("/rest/*").with(GuiceContainer.class, params);
    }
}
