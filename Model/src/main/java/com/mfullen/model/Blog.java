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
public class Blog extends TrackableModel
{
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Post> posts;

    public Collection<Post> getPosts()
    {
        return posts;
    }

    public void setPosts(Collection<Post> posts)
    {
        this.posts = posts;
    }
}
