package com.mfullen.rest;

import com.google.inject.Scopes;
import com.google.inject.servlet.SessionScoped;
import com.mfullen.repositories.BlogRepository;
import com.mfullen.repositories.UserRepository;
import com.mfullen.repositories.jpa.JpaBlogRepository;
import com.mfullen.repositories.jpa.JpaUserRepository;
import com.mfullen.rest.model.mapping.IMappingService;
import com.mfullen.rest.model.mapping.RestMapper;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

/**
 *
 * @author mfullen
 */
public class GuiceMfullenRest extends JerseyServletModule
{
    final ResourceConfig rc = new PackagesResourceConfig("com.mfullen.rest");

    @Override
    protected void configureServlets()
    {
        bind(IMappingService.class).to(RestMapper.class);
        bind(BlogRepository.class).to(JpaBlogRepository.class);//.in(SessionScoped.class);
        bind(UserRepository.class).to(JpaUserRepository.class);//.in(SessionScoped.class);

        bind(JacksonJsonProvider.class).in(Scopes.SINGLETON);

        final Map<String, String> params = new HashMap<String, String>();
        params.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
        params.put("com.sun.jersey.config.property.packages", "com.mfullen.rest.resources");

        for (Class<?> resource : rc.getClasses())
        {
            System.out.println("Binding resource: " + resource.getName());
            bind(resource);
        }
        serve("/rest/*").with(GuiceContainer.class, params);
    }
}
