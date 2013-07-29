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
    @Override
    protected void configure()
    {
        map().setUserId(source.getUser().getId());
        map().setCreationDate(source.getDatePosted());
        map().setId(source.getId());
        map().setTitle(source.getTitle());
        using(MapperUtils.getObjectListToIdListConverter()).map(source.getPosts()).setPostIds(null);
    }
}
