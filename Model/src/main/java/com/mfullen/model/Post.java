package com.mfullen.model;

import java.util.Collection;
import java.util.Date;

/**
 *
 * @author mfullen
 */
public class Post extends AbstractModel
{
    private Date datePosted;
    private String body;
    private Collection<Comment> comments;

    public String getBody()
    {
        return body;
    }

    public Collection<Comment> getComments()
    {
        return comments;
    }

    public Date getDatePosted()
    {
        return datePosted;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public void setComments(Collection<Comment> comments)
    {
        this.comments = comments;
    }

    public void setDatePosted(Date datePosted)
    {
        this.datePosted = datePosted;
    }
}
