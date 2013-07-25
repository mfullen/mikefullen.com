package com.mfullen.model;

import javax.persistence.MappedSuperclass;

/**
 *
 * @author mfullen
 */
@MappedSuperclass
public abstract class ContentModel extends TrackableModel
{
    private String content;

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
