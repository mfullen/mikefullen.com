package com.mfullen.rest.services.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mfullen
 */
public class MockEmailGatewayServiceImpl implements EmailGatewayService
{
    private static Logger logger = LoggerFactory.getLogger(MockEmailGatewayServiceImpl.class);

    @Override
    public void sendVerificationToken(EmailServiceTokenModel model)
    {
        logger.info("Email Address: {} Url: {}", model.getEmailAddress(), model.getHostNameUrl());
        logger.info("Token: {} EncodedToken: {} TokenType: {}", model.getToken(), model.getEncodedToken(), model.getTokenType());
    }
}
