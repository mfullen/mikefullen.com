package com.mfullen.rest.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author mfullen
 */
public class CreateUserRequest
{
    @NotNull
    @Valid
    private String username;
    @NotNull
    @Valid
    private String password;
    @NotNull
    @Email
    private String email;

    public String getPassword()
    {
        return password;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
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
