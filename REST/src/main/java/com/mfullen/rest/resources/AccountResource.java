package com.mfullen.rest.resources;

import com.mfullen.model.Role;
import com.mfullen.rest.authorization.AuthenticatedUserToken;
import com.mfullen.rest.request.CreateUserRequest;
import com.mfullen.rest.request.LoginRequest;
import com.mfullen.rest.services.account.UserService;
import com.mfullen.rest.services.verification.VerificationTokenService;
import java.net.URI;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
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
    @Inject
    private VerificationTokenService verificationTokenService;
    @Context
    private UriInfo uriInfo;

    protected void setUserServiceProvider(UserService userService)
    {
        this.userService = userService;
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
        AuthenticatedUserToken token = userService.register(request, Role.AUTHENTICATED);
        verificationTokenService.sendEmailRegistrationToken(token.getUsername());
        URI location = uriInfo.getAbsolutePathBuilder().path(token.getUserId().toString()).build();
        return Response.created(location).entity(token).build();
    }

    @Path("login")
    @POST
    @PermitAll
    public Response login(LoginRequest request)
    {
        AuthenticatedUserToken token = userService.login(request);
        URI location = UriBuilder.fromPath(uriInfo.getBaseUri() + "account/" + token.getUserId()).build();
        return Response.ok().entity(token).contentLocation(location).build();
    }
}
