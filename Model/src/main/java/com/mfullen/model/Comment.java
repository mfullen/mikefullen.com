package com.mfullen.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author mfullen
 */
@Entity
public class Comment extends ContentModel
{
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public List<Comment> getComments()
    {
        return comments;
    }

    public void setComments(List<Comment> comments)
    {
        this.comments = comments;
    }

    public Post getPost()
    {
        return post;
    }

    public void setPost(Post post)
    {
        this.post = post;
    }
}
