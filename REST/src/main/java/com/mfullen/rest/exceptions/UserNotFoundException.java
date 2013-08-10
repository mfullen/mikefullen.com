package com.mfullen.rest.exceptions;

public class UserNotFoundException extends BaseWebApplicationException
{
    public UserNotFoundException()
    {
        super(404, "40402", "User Not Found", "No User could be found for that Id");
    }
}
