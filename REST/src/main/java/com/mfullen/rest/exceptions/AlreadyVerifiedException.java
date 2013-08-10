package com.mfullen.rest.exceptions;

public class AlreadyVerifiedException extends BaseWebApplicationException
{
    public AlreadyVerifiedException()
    {
        super(409, "40905", "Already verified", "The token has already been verified");
    }
}
