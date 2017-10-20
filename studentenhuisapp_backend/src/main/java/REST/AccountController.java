package REST;

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

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Account edit(Account account) {
        return accountService.edit(account);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response requestLogin() {
        return Response.seeOther(accountService.requestLogin()).build();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response accountLoggedin(String authorizationCode) {
        Account account = accountService.accountAuthorized(authorizationCode);
        return Response.ok(account).header("Authorization", accountService.getAccessToken(account.getId())).build();
    }
}
