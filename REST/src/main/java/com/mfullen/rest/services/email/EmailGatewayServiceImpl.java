package com.mfullen.rest.services.email;

import javax.inject.Inject;

/**
 *
 * @author mfullen
 */
public class EmailGatewayServiceImpl implements EmailGatewayService
{
    private MailService mailService;

    @Inject
    public EmailGatewayServiceImpl(MailService mailService)
    {
        this.mailService = mailService;
    }

    @Override
    public void sendVerificationToken(EmailServiceTokenModel model)
    {
        String content = model.getHostNameUrl() + "/verify/tokens/" + model.getEncodedToken()
                + "\r\n" + "token: " + model.getToken()
                + "\r\n" + "token type: " + model.getTokenType();
        mailService.sendMail(model.getEmailAddress(), "Verify Account", content);
    }
}
