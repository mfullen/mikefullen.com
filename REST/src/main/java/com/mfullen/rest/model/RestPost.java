package com.mfullen.rest.model;

import com.mfullen.model.AbstractModel;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author mfullen
 */
public class RestPost extends AbstractModel
{
    private String content;
    private Timestamp creationDate;
    private Long blogId;
    private List<Long> commentIds;
    private Long userId;

    public Long getBlogId()
    {
        return blogId;
    }

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

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }
}
