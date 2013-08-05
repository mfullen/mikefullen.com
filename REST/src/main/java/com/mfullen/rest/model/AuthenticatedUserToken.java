package com.mfullen.rest.model;

/**
 *
 * @author mfullen
 */
public class AuthenticatedUserToken
{
    private Long userId;
    private String token;

    public AuthenticatedUserToken()
    {
    }

    public AuthenticatedUserToken(Long userId, String token)
    {
        this.userId = userId;
        this.token = token;
    }

    public String getToken()
    {
        return token;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }
}
