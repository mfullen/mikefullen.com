package com.mfullen.rest.model;

import com.mfullen.model.AbstractModel;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author mfullen
 */
public class RestComment extends AbstractModel
{
    private String content;
    private Timestamp creationDate;
    private List<Long> commentIds;
    private Long createdById;
    private Long postId;

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Timestamp getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate)
    {
        this.creationDate = creationDate;
    }

    public List<Long> getCommentIds()
    {
        return commentIds;
    }

    public void setCommentIds(List<Long> commentIds)
    {
        this.commentIds = commentIds;
    }

    public Long getCreatedById()
    {
        return createdById;
    }

    public void setCreatedById(Long createdById)
    {
        this.createdById = createdById;
    }

    public Long getPostId()
    {
        return postId;
    }

    public void setPostId(Long postId)
    {
        this.postId = postId;
    }
}
