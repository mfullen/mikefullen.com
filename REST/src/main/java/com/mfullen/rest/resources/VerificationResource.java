package com.mfullen.rest.resources;

import com.mfullen.rest.request.EmailVerificationRequest;
import com.mfullen.rest.services.VerificationTokenService;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author mfullen
 */
@Path("/verify")
public class VerificationResource extends AbstractREST
{
    @Inject
    private VerificationTokenService tokenService;

    protected void setTokenService(VerificationTokenService tokenService)
    {
        this.tokenService = tokenService;
    }

    @PermitAll
    @Path("tokens/{token}")
    @POST
    public Response verifyToken(@PathParam("token") String token)
    {
        tokenService.verify(token);
        return Response.ok().build();
    }

    @PermitAll
    @Path("tokens")
    @POST
    public Response sendEmailToken(EmailVerificationRequest request)
    {
        tokenService.generateEmailVerificationToken(request.getEmailAddress());
        return Response.ok().build();
    }
}
