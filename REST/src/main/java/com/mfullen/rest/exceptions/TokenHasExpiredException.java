package com.mfullen.rest.exceptions;

public class TokenHasExpiredException extends BaseWebApplicationException
{
    public TokenHasExpiredException()
    {
        super(403, "40304", "Token has expired", "An attempt was made to load a token that has expired");
    }
}
