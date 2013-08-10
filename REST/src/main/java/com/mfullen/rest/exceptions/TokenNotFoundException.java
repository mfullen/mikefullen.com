package com.mfullen.rest.exceptions;

public class TokenNotFoundException extends BaseWebApplicationException
{
    public TokenNotFoundException()
    {
        super(404, "40407", "Token Not Found", "No token could be found for that Id");
    }
}
