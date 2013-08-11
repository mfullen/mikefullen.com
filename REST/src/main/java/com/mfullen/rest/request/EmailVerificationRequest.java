package com.mfullen.rest.request;

import javax.validation.constraints.NotNull;

/**
 *
 * @author mfullen
 */
public class EmailVerificationRequest
{
    @NotNull
    private String emailAddress;

    public EmailVerificationRequest(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }
}
