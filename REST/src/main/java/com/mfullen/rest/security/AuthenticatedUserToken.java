package com.mfullen.rest.security;

/**
 *
 * @author mfullen
 */
public class AuthenticatedUserToken
{
    private Long userId;
    private String token;
    private String username;

    public AuthenticatedUserToken()
    {
    }

    public AuthenticatedUserToken(Long userId, String token, String username)
    {
        this.userId = userId;
        this.token = token;
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
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
