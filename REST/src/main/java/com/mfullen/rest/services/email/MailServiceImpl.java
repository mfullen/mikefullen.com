package com.mfullen.rest.services.email;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mfullen
 */
class MailServiceImpl implements MailService
{
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Override
    public void sendMail(String email, String subject, String content)
    {
        try
        {
            final String username = "no-reply@mikefullen.com";
            final String password = "password";

            String dest = email;

            boolean isSmtpTls = true;
            String smtpHost = "smtp.gmail.com";
            int smtpPort = 25;

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "" + isSmtpTls);
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.port", "" + smtpPort);

            Session session = Session.getInstance(props, new Authenticator()
            {
                @Override
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username, "EmailService"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(dest));
            message.setSubject("account verification - " + subject);
            message.setContent(content, "text/html; charset=utf-8");

            Transport.send(message);
        }
        catch (UnsupportedEncodingException | MessagingException ex)
        {
            logger.error("Error sending email", ex);
        }
    }
}
