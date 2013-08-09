package com.mfullen.rest.resources;

import com.mfullen.model.Role;
import com.mfullen.rest.model.AuthenticatedUserToken;
import com.mfullen.rest.model.request.CreateUserRequest;
import com.mfullen.rest.model.request.LoginRequest;
import com.mfullen.rest.services.UserService;
import java.net.URI;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
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

    public void setUserServiceProvider(UserService userServiceProvider)
    {
        this.userService = userServiceProvider;
    }

    @GET
    @Path("/test")
    @RolesAllowed(
    {
        "AUTHENTICATED"
    })
    public Response test()
    {
        return Response.ok().build();
    }

    @POST
    @Path("/register")
    @PermitAll
    public Response register(CreateUserRequest request)
    {
        //UserService userService = userServiceProvider.get();
        AuthenticatedUserToken token = userService.register(request, Role.AUTHENTICATED);
        //send registration email?
        URI location = uriInfo.getAbsolutePathBuilder().path(token.getUserId().toString()).build();
        return Response.created(location).entity(token).build();
    }

    @Path("login")
    @POST
    @PermitAll
    public Response login(LoginRequest request)
    {
        //UserService userService = userServiceProvider.get();
        AuthenticatedUserToken token = userService.login(request);
        URI location = UriBuilder.fromPath(uriInfo.getBaseUri() + "user/" + token.getUserId()).build();
        return Response.ok().entity(token).contentLocation(location).build();
    }
}
