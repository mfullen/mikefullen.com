package com.mfullen.rest.services;

/**
 *
 * @author mfullen
 */
public interface EmailGatewayService
{
    void sendVerificationToken(EmailServiceTokenModel model);
}
