package com.mfullen.rest.services.email;

/**
 *
 * @author mfullen
 */
public interface MailService
{
    void sendMail(String email, String subject, String content);
}
