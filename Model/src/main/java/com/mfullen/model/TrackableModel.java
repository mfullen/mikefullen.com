package com.mfullen.model;

import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 *
 * @author mfullen
 */
@MappedSuperclass
public abstract class TrackableModel extends AbstractModel
{
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserModel user;
    private Timestamp datePosted;

    public Timestamp getDatePosted()
    {
        return datePosted;
    }

    public void setDatePosted(Timestamp datePosted)
    {
        this.datePosted = datePosted;
    }

    public UserModel getUser()
    {
        return user;
    }

    public void setUser(UserModel user)
    {
        this.user = user;
    }
}
