package com.mfullen.rest.authorization;

import com.mfullen.model.Role;
import com.mfullen.model.UserModel;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author mfullen
 */
public class PrincipalUserImpl implements PrincipalUser
{
    private final UserModel user;

    public PrincipalUserImpl(UserModel user)
    {
        this.user = user;
    }

    @Override
    public Collection<String> getRoles()
    {
        Collection<String> roles = new ArrayList<>();
        for (Role role : user.getUserRoles())
        {
            roles.add(role.name());
        }
        return roles;
    }

    @Override
    public String getName()
    {
        return user.getUserName();
    }
}
