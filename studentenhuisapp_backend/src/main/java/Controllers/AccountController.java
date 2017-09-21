package Controllers;

import Models.Account;
import Service.AccountService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

@Stateless
@Path("/accounts")
public class AccountController {

    @Inject
    private AccountService accountService;

    @Context
    private UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Account account) {
        final Account newAccount = accountService.create(account);
        System.out.println("Pre uriinfo message");
        final URI uri = uriInfo.getAbsolutePathBuilder().path(newAccount.getId() + "").build();
        System.out.println("Post uriinfo message");
        return Response.created(uri).entity(newAccount).build();
    }
}
