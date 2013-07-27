package com.mfullen.rest.model;

import com.mfullen.model.AbstractModel;

/**
 *
 * @author mfullen
 */
public class RestUser extends AbstractModel
{
    private String username;

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }
}
