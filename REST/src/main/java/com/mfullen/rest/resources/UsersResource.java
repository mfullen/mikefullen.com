package com.mfullen.rest.resources;

import com.mfullen.model.UserModel;
import com.mfullen.repositories.UserRepository;
import com.mfullen.rest.model.RestUser;
import com.mfullen.rest.model.mapping.IMappingService;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author mfullen
 */
@Path("/profiles")
public class UsersResource extends AbstractREST
{
    @Inject
    private UserRepository userRepository;
    @Inject
    private IMappingService mappingService;

    @Path("/")
    @GET
    @RolesAllowed(
    {
        "AUTHENICATED"
    })
    public Response getAllProfiles()
    {
        Collection<UserModel> all = this.userRepository.getAll();
        Collection<RestUser> restUsers = new ArrayList<>();

        for (UserModel userModel : all)
        {
            restUsers.add(mappingService.getMapper().map(userModel, RestUser.class));
        }

        return Response.ok(restUsers).build();
    }
}
