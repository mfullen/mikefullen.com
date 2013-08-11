package com.mfullen.rest.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author mfullen
 */
public class LoginRequest
{
    @NotNull
    @Valid
    @Length(min = 4, max = 50)
    private String username;
    @NotNull
    @Valid
    @Length(min = 6, max = 30)
    private String password;

    public LoginRequest()
    {
    }

    public LoginRequest(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }

    public String getUsername()
    {
        return username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
}
