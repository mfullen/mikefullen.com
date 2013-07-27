package com.mfullen.rest.model;

import com.mfullen.model.AbstractModel;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author mfullen
 */
public class RestBlog extends AbstractModel
{
    private String name;
    private Timestamp creationDate;
    private List<Long> postIds;
    private Long userId;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Timestamp getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate)
    {
        this.creationDate = creationDate;
    }

    public List<Long> getPostIds()
    {
        return postIds;
    }

    public void setPostIds(List<Long> postIds)
    {
        this.postIds = postIds;
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
