package com.mfullen.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 *
 * @author mfullen
 */
@Entity
public class Blog extends TrackableModel
{
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "blog",
               fetch = FetchType.LAZY)
    private List<Post> posts;
    private String title;

    public List<Post> getPosts()
    {
        return posts;
    }

    public void setPosts(List<Post> posts)
    {
        this.posts = posts;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
