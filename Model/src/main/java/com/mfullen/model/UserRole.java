package com.mfullen.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author mfullen
 */
@Entity
public class UserRole extends AbstractModel
{
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;
    @Column(name = "roleName", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public Role getRole()
    {
        return role;
    }

    public UserModel getUser()
    {
        return user;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    public void setUser(UserModel user)
    {
        this.user = user;
    }
}
