package Controller;

import Controllers.AccountController;
import Models.Account;
import Service.AccountService;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.Path;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest extends JerseyTest {

    @Mock
    private AccountService service;

    @Override
    protected Application configure() {
        final ResourceConfig config = new ResourceConfig(AccountController.class);
        config.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(service).to(AccountService.class);
            }
        });
        return config;
    }

    @Test
    public void createUserTest() throws Exception {
        final Account testAccount = new Account("Maiko", "maiko999@mail.nl");
        when(service.create(testAccount))
                .thenReturn(testAccount);

        System.out.println("Create account");
        //Create a new Account
        final Response correctResult = target("/accounts")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(testAccount));
        Assert.assertEquals(HTTP_CREATED, correctResult.getStatus());
        Assert.assertEquals(testAccount, correctResult.readEntity(Account.class));

        System.out.println("Create duplicate account");
        //Try to create a duplicate product
        final Response duplicateResult = target("/accounts")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(testAccount));
        Assert.assertEquals(HTTP_INTERNAL_ERROR, duplicateResult.getStatus());

        System.out.println("Create empty account");
        //Try to create a new empty product
        final Response faultyResult = target("/accounts")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(""));
        Assert.assertEquals(HTTP_INTERNAL_ERROR, faultyResult.getStatus());

        System.out.println("Create null account");
        //Try to create a new null product
        final Response faultyResult2 = target("/accounts")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(null));
        Assert.assertEquals(HTTP_INTERNAL_ERROR, faultyResult2.getStatus());
    }
}
