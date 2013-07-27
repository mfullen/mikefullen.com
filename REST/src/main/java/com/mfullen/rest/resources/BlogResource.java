package com.mfullen.rest.resources;

import com.mfullen.model.Blog;
import com.mfullen.repositories.BlogRepository;
import com.mfullen.rest.model.RestBlog;
import com.mfullen.rest.model.mapping.IMappingService;
import java.util.ArrayList;
import java.util.Collection;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.springframework.stereotype.Component;

/**
 *
 * @author mfullen
 */
@Path("/blogs")
@Component
public class BlogResource extends AbstractREST
{
    @Inject
    private BlogRepository blogRepository;
    @Inject
    private IMappingService mappingService;

    @Path("/")
    @GET
    public Response getAllBlogs()
    {
        Collection<Blog> blogs = this.blogRepository.getAll();

        Collection<RestBlog> restBlogs = new ArrayList<RestBlog>();

        for (Blog regBlog : blogs)
        {
            restBlogs.add(mappingService.getMapper().map(regBlog, RestBlog.class));
        }

        return Response.ok(restBlogs).build();
    }
}
