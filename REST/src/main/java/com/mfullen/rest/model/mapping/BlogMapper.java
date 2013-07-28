package com.mfullen.rest.model.mapping;

import com.mfullen.model.Blog;
import com.mfullen.rest.model.RestBlog;
import org.modelmapper.PropertyMap;

/**
 *
 * @author mfullen
 */
public class BlogMapper extends PropertyMap<Blog, RestBlog>
{
    private ObjectListToIdListConverter objectListToIdListConverter = new ObjectListToIdListConverter();

    @Override
    protected void configure()
    {
        map().setUserId(source.getUser().getId());
        map().setCreationDate(source.getDatePosted());
        map().setId(source.getId());
        map().setTitle(source.getTitle());
        using(objectListToIdListConverter).map(source.getPosts()).setPostIds(null);
    }
}
