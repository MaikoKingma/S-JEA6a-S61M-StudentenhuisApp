package Service;

import DAO.IUserDao;
import JMS.JMSBrokerGateway;
import JMS.Message.Events;
import Models.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

//Note: always check if events are send to the JMS broker
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private IUserDao userDao;
    @Mock
    private JMSBrokerGateway jmsBroker;

    @Test
    public void createUserTest() throws Exception {
        final User testUser = new User("Maiko", "maiko@mail.nl");
        Assert.assertEquals(testUser, userService.create(testUser));
        verify(jmsBroker, times(1))
                .sendMessage(Mockito.anyString(), eq(Events.ACCOUNT_CREATED), eq(testUser.getId()));
    }
}
