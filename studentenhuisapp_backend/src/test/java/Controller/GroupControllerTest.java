package Controller;

import Controllers.GroupController;
import Models.*;
import Service.GroupService;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;

import static java.net.HttpURLConnection.*;

@RunWith(MockitoJUnitRunner.class)
public class GroupControllerTest extends JerseyTest {

    @Mock
    private GroupService service;

    @Override
    protected Application configure() {
        final ResourceConfig config = new ResourceConfig(GroupController.class);
        config.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(service).to(GroupService.class);
            }
        });
        return config;
    }

    @Test
    public void createGroupTest() throws Exception {
        final Account account = new Account("Maiko", "maiko999@mail.nl");
        final Group testGroup = new Group("Studentenhuis");
        final Group returnGroup = testGroup;
        returnGroup.addAccount(account);
        Mockito.when(service.create(testGroup, account.getId()))
                .thenReturn(returnGroup);

        final Response correctResult = target("/groups")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(testGroup));
        Assert.assertEquals("Create Group did not give the correct response.",
                HTTP_CREATED,
                correctResult.getStatus());
        Assert.assertEquals("Create Group did not return the correct group.",
                testGroup,
                correctResult.readEntity(Group.class));

        final Response faultyResult = target("/groups")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(""));
        Assert.assertEquals("Empty group was created",
                HTTP_INTERNAL_ERROR,
                faultyResult.getStatus());

        final Response faultyResult2 = target("/groups")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(null));
        Assert.assertEquals("Null group was created",
                HTTP_INTERNAL_ERROR,
                faultyResult2.getStatus());
    }
}
