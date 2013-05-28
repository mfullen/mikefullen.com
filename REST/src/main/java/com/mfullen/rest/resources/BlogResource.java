package com.mfullen.rest.resources;

import com.mfullen.model.Blog;
import com.mfullen.repositories.BlogRepository;
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
    BlogRepository blogRepository;

    @Path("/")
    @GET
    public Response getAllBlogs()
    {
        Collection<Blog> blogs = this.blogRepository.getAll();
        return Response.ok(blogs).build();
    }
}
