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
        final URI uri = uriInfo.getAbsolutePathBuilder().path(newAccount.getId() + "").build();
        return Response.created(uri).entity(newAccount).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Account edit(Account account) {
        return accountService.edit(account);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response login() {
        return Response.seeOther(accountService.getAuthorizationUri()).build();
    }
}
