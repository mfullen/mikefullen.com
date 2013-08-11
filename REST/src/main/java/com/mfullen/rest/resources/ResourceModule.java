package com.mfullen.rest.resources;

import com.google.inject.AbstractModule;
import com.mfullen.repositories.BlogRepository;
import com.mfullen.repositories.UserRepository;
import com.mfullen.repositories.VerificationTokenRepository;
import com.mfullen.repositories.jpa.JpaBlogRepository;
import com.mfullen.repositories.jpa.JpaUserRepository;
import com.mfullen.repositories.jpa.JpaVerificationTokenRepository;
import com.mfullen.rest.model.mapping.IMappingService;
import com.mfullen.rest.model.mapping.RestMapper;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mfullen
 */
public class ResourceModule extends AbstractModule
{
    private static final Logger logger = LoggerFactory.getLogger(ResourceModule.class);
    public static final String REST_PACKAGE = "com.mfullen.rest";
    final ResourceConfig rc = new PackagesResourceConfig(REST_PACKAGE);

    @Override
    protected void configure()
    {
        bind(IMappingService.class).to(RestMapper.class);
        bind(BlogRepository.class).to(JpaBlogRepository.class);
        bind(UserRepository.class).to(JpaUserRepository.class);
        bind(VerificationTokenRepository.class).to(JpaVerificationTokenRepository.class);


        for (Class<?> resource : rc.getClasses())
        {
            logger.debug("Binding resource: " + resource.getName());
            bind(resource);
        }
    }
}
