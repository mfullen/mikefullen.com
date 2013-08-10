package com.mfullen.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author mfullen
 */
@Entity
public class UserModel extends AbstractModel
{
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    @Column(unique = true)
    private String apiKey;
    @OneToMany(mappedBy = "user", cascade =
    {
        CascadeType.PERSIST, CascadeType.REMOVE
    })
    private Set<UserRole> roles = new HashSet<>();

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getApiKey()
    {
        return apiKey;
    }

    public void setApiKey(String apiKey)
    {
        this.apiKey = apiKey;
    }

    public Set<UserRole> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<UserRole> roles)
    {
        this.roles = roles;
    }

    public Set<Role> getUserRoles()
    {
        Set<Role> uroles = new HashSet<>();

        if (this.getRoles() != null)
        {
            for (UserRole userRole : this.getRoles())
            {
                uroles.add(userRole.getRole());
            }
        }
        return uroles;
    }
}
