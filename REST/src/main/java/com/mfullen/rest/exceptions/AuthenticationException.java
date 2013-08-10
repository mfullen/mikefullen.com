package com.mfullen.rest.exceptions;

public class AuthenticationException extends BaseWebApplicationException
{
    public AuthenticationException()
    {
        super(401, "40102", "Authentication Error", "Authentication Error. The username or password were incorrect");
    }
}
