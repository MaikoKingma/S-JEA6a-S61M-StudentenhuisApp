package Controller;

import REST.AccountController;
import Models.Account;
import Service.AccountService;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.net.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest extends JerseyTest {

    @InjectMocks
    private AccountController controller;

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
    public void editAccountTest() throws Exception {
        final Account testAccount = new Account("Maiko", "754221452");
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
    public void requestLoginAccountTest() throws Exception {
        URI uri = new URI("www.google.nl");
        Mockito.when(service.requestLogin())
                .thenReturn(uri);

        //Integration testing not necessary since method forwards the user to a external url
        Response response = controller.requestLogin();

        Assert.assertEquals("Login did not give the correct response code.",
                HttpURLConnection.HTTP_SEE_OTHER,
                response.getStatus());
        Assert.assertEquals("Login did not give the correct response code.",
                uri,
                response.getLocation());
    }
}
