package com.mfullen.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author mfullen
 */
@Entity
public class Post extends ContentModel
{
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post",
               fetch = FetchType.LAZY)
    private List<Comment> comments;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;

    public Blog getBlog()
    {
        return blog;
    }

    public void setBlog(Blog blog)
    {
        this.blog = blog;
    }

    public List<Comment> getComments()
    {
        return comments;
    }

    public void setComments(List<Comment> comments)
    {
        this.comments = comments;
    }
}
