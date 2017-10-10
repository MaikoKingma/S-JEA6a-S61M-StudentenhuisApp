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
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;

import java.net.HttpURLConnection;
import java.net.URI;

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
    public void createAccountTest() throws Exception {
            final Account testAccount = new Account("Maiko", "maiko999@mail.nl");
            Mockito.when(service.create(testAccount))
                    .thenReturn(testAccount);

            final Response correctResult = target("/accounts")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(testAccount));
            Assert.assertEquals("Create Account did not give the correct response.",
                    HttpURLConnection.HTTP_CREATED,
                    correctResult.getStatus());
            Assert.assertEquals("Create Account did not return the correct account.",
                    testAccount,
                    correctResult.readEntity(Account.class));

            final Response faultyResult = target("/accounts")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(""));
            Assert.assertEquals("Empty account was created",
                    HttpURLConnection.HTTP_INTERNAL_ERROR,
                    faultyResult.getStatus());

            final Response faultyResult2 = target("/accounts")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(null));
            Assert.assertEquals("Null account was created",
                    HttpURLConnection.HTTP_INTERNAL_ERROR,
                    faultyResult2.getStatus());
    }

    @Test
    public void editAccountTest() throws Exception {
        final Account testAccount = new Account("Maiko", "maiko999@mail.nl");
        testAccount.setId(1);
        testAccount.setActive(true);
        Mockito.when(service.edit(Mockito.any(Account.class)))
                .thenReturn(testAccount);

        final Response correctResult = target("/accounts")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(testAccount));
        Assert.assertEquals("Account was not modified",
                HttpURLConnection.HTTP_OK,
                correctResult.getStatus());
        Assert.assertEquals("Account was not the same",
                testAccount,
                correctResult.readEntity(Account.class));

        final Response faultyResult = target("/accounts")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(""));
        Assert.assertEquals("Empty account was modified",
                HttpURLConnection.HTTP_NO_CONTENT,
                faultyResult.getStatus());
    }

    @Test
    public void loginAccountTest() throws Exception {
        URI uri = new URI("");
        Mockito.when(service.getAuthorizationUri())
                .thenReturn(uri);

        final Response correctResult = target("/accounts")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals("Wrong status was returned",
                HttpURLConnection.HTTP_SEE_OTHER,
                correctResult.getStatus());
//        Assert.assertEquals("Wrong account was returned",
//                testAccount,
//                correctResult.readEntity(Account.class));
    }
}
