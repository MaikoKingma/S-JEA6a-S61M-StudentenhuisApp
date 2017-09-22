package Service;

import DAO.IAccountDao;
import JMS.JMSBrokerGateway;
import JMS.Message.Events;
import Models.Account;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

//Note: always check if events are send to the JMS broker
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private IAccountDao userDao;
    @Mock
    private JMSBrokerGateway jmsBroker;

    @Test
    public void createUserTest() throws Exception {
        final Account testAccount = new Account("Maiko", "maiko@mail.nl");
        Assert.assertEquals(testAccount, accountService.create(testAccount));
        verify(jmsBroker, times(1))
                .sendMessage(Mockito.anyString(), eq(Events.ACCOUNT_CREATED), eq(testAccount.getId()));
    }
}
