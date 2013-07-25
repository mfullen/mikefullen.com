package com.mfullen.model;

import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 *
 * @author mfullen
 */
@Entity
public class Post extends ContentModel
{
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Comment> comments;

    public Collection<Comment> getComments()
    {
        return comments;
    }

    public void setComments(Collection<Comment> comments)
    {
        this.comments = comments;
    }
}
