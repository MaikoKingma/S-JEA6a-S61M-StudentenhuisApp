package Controllers;

import Models.User;
import Service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

@Stateless
@Path("/users")
public class UserController {

    @Inject
    private UserService userService;

    @Context
    private UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(User user) {
        final User newUser = userService.create(user);
        final URI uri = uriInfo.getAbsolutePathBuilder().path(newUser.getId() + "").build();
        return Response.created(uri).entity(newUser).build();
    }
}
