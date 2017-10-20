package REST;

import DataTransferObject.NewGroupInfo;
import Models.Group;
import Service.GroupService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

@Stateless
@Path("/groups")
public class GroupController {

    @Inject
    private GroupService groupService;

    @Context
    private UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(NewGroupInfo body) {
        final Group newGroup = groupService.create(body.getGroup(), body.getAccountId());
        final URI uri = uriInfo.getAbsolutePathBuilder().path(newGroup.getId() + "").build();
        return Response.created(uri).entity(newGroup).build();
    }
}
