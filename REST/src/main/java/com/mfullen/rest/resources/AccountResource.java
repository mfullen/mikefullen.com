package com.mfullen.rest.resources;

import com.mfullen.rest.model.AuthenticatedUserToken;
import com.mfullen.rest.model.request.CreateUserRequest;
import com.mfullen.rest.model.request.LoginRequest;
import com.mfullen.rest.services.UserService;
import java.net.URI;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author mfullen
 */
@Path("/account")
public class AccountResource extends AbstractREST
{
    @Inject
    private UserService userService;
    @Context
    private UriInfo uriInfo;

    @Inject
    public AccountResource(UriInfo uriInfo)
    {
        this.uriInfo = uriInfo;
    }

    @POST
    @Path("/register")
    public Response register(CreateUserRequest request)
    {
        AuthenticatedUserToken token = userService.register(request);
        //send registration email?
        URI location = uriInfo.getAbsolutePathBuilder().path(token.getUserId().toString()).build();
        return Response.created(location).entity(token).build();
    }

    @Path("login")
    @POST
    public Response login(LoginRequest request)
    {
        AuthenticatedUserToken token = userService.login(request);
        URI location = UriBuilder.fromPath(uriInfo.getBaseUri() + "user/" + token.getUserId()).build();
        return Response.ok().entity(token).contentLocation(location).build();
    }
}
