package com.mfullen.rest;

import com.mfullen.model.Role;
import java.security.Principal;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author mfullen
 */
class SecurityContextImpl implements SecurityContext
{
    private final PrincipalUser externalUser;

    public SecurityContextImpl(PrincipalUser externalUser)
    {
        this.externalUser = externalUser;
    }

    @Override
    public Principal getUserPrincipal()
    {
        return externalUser;
    }

    @Override
    public boolean isUserInRole(String role)
    {
        //If anonymous then return true
        if (role.equalsIgnoreCase(Role.ANON.name()))
        {
            return true;
        }
        if (externalUser == null)
        {
            throw new NullPointerException("TBD: User is null");
        }
        for (String userRole : externalUser.getRoles())
        {
            for (Role roll : Role.values())
            {
                if (roll.name().equalsIgnoreCase(userRole))
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isSecure()
    {
        return false;
    }

    @Override
    public String getAuthenticationScheme()
    {
        return SecurityContext.BASIC_AUTH;
    }
}
