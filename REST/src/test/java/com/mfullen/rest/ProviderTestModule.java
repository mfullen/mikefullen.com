package com.mfullen.rest;

import static org.mockito.Mockito.*;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mfullen.repositories.BlogRepository;
import com.mfullen.repositories.UserRepository;
import com.mfullen.repositories.VerificationTokenRepository;
import com.mfullen.rest.services.account.UserService;
import com.mfullen.rest.services.email.EmailGatewayService;
import com.mfullen.rest.services.verification.VerificationTokenService;

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
    @Singleton
    VerificationTokenService providerMockVerificationTokenService()
    {
        return mock(VerificationTokenService.class);
    }

    @Provides
    @Singleton
    EmailGatewayService providerMockEmailGatewayService()
    {
        return mock(EmailGatewayService.class);
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

    @Provides
    VerificationTokenRepository providerMockVerificationTokenRepository()
    {
        return mock(VerificationTokenRepository.class);

    }

    @Override
    protected void configure()
    {
    }
}
