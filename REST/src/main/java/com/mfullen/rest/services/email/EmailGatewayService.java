package com.mfullen.rest.services.email;

/**
 *
 * @author mfullen
 */
public interface EmailGatewayService
{
    void sendVerificationToken(EmailServiceTokenModel model);
}
