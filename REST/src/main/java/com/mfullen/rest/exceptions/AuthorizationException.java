package com.mfullen.rest.exceptions;

public class AuthorizationException extends BaseWebApplicationException
{
    public AuthorizationException(String applicationMessage)
    {
        super(403, "40301", "Not authorized", applicationMessage);
    }
}
