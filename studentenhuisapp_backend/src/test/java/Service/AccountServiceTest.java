package Service;

import DAO.IAccountDao;
import JMS.JMSBrokerGateway;
import JMS.Message.Events;
import Models.Account;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

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
        Assert.assertEquals("User was not created",
                testAccount,
                accountService.create(testAccount));
        Mockito.verify(jmsBroker)
                .sendMessage(Mockito.anyString(), Mockito.eq(Events.ACCOUNT_CREATED), Mockito.eq(testAccount.getId()));
    }
}
