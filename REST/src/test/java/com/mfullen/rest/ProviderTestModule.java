package com.mfullen.rest;

import static org.mockito.Mockito.*;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mfullen.repositories.BlogRepository;
import com.mfullen.repositories.UserRepository;
import com.mfullen.rest.services.UserService;

/**
 *
 * @author mfullen
 */
public class ProviderTestModule extends AbstractModule
{
    @Provides
    @Singleton
    UserService providerMockUserService()
    {
        return mock(UserService.class);
    }

    @Provides
    BlogRepository providerMockBlogRepository()
    {
        BlogRepository blogRepository = mock(BlogRepository.class);
        return blogRepository;
    }

    @Provides
    UserRepository providerMockUserRepository()
    {
        UserRepository userRepository = mock(UserRepository.class);
        return userRepository;
    }

    @Override
    protected void configure()
    {
    }
}
