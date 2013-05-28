package com.mfullen.model;

import java.util.Collection;
import java.util.Date;

/**
 *
 * @author mfullen
 */
public class Blog extends AbstractModel
{
    private Date datePosted;
    private String title;
    private Collection<Post> posts;

    public Collection<Post> getPosts()
    {
        return posts;
    }

    public void setPosts(Collection<Post> posts)
    {
        this.posts = posts;
    }

    public Date getDatePosted()
    {
        return datePosted;
    }

    public String getTitle()
    {
        return title;
    }

    public void setDatePosted(Date datePosted)
    {
        this.datePosted = datePosted;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
