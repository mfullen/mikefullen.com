package com.mfullen.rest.resources;

import com.google.common.base.Preconditions;
import com.mfullen.model.Blog;
import com.mfullen.repositories.BlogRepository;
import com.mfullen.rest.model.RestBlog;
import com.mfullen.rest.model.mapping.IMappingService;
import com.mfullen.rest.model.request.CreateBlogRequest;
import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author mfullen
 */
@Path("/blogs")
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

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id)
    {
        Preconditions.checkNotNull(id);
        Blog byId = this.blogRepository.getById(id);

        if (byId == null)
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }


        return Response.ok(this.mappingService.getMapper().map(byId, RestBlog.class)).build();
    }

    @POST
    @Path("/create")
    public Response addBlog(CreateBlogRequest request, @Context UriInfo uriInfo)
    {
        Preconditions.checkNotNull(request);
        Preconditions.checkNotNull(request.getTitle());

        Blog blog = new Blog();
        blog.setTitle(request.getTitle());
        blog.setDatePosted(new Timestamp(System.currentTimeMillis()));
        //blog.setUser(null);
        Blog newBlog = this.blogRepository.add(blog);
        URI newUserUri = uriInfo.getBaseUriBuilder().path(BlogResource.class).path("/" + newBlog.getId()).build();
        ResponseBuilder response = Response.created(newUserUri);
        response.entity(newBlog);
        return response.build();
    }
}
