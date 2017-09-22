package Service;

import DAO.IAccountDao;
import JMS.JMSBrokerGateway;
import JMS.Message.Events;
import Models.Account;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static com.github.stefanbirkner.fishbowl.Fishbowl.exceptionThrownBy;

//Note: always check if events are send to the JMS broker
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private IAccountDao userDao;
    @Mock
    private JMSBrokerGateway jmsBroker;

    List<Account> accounts;

    public AccountServiceTest() {
        accounts = new ArrayList<Account>() {{
            add(new Account("Maiko", "maiko@mail.nl"));
            add(new Account("Pim", "pim@mail.nl"));
            add(new Account("Loek", "loek@mail.nl"));
        }};
    }

    @Test
    public void createAccountTest() throws Exception {
        final Account testAccount = new Account("Maiko", "maiko@mail.nl");
        Assert.assertEquals("User was not created",
                testAccount,
                accountService.create(testAccount));
        Mockito.verify(jmsBroker)
                .sendMessage(Mockito.anyString(), Mockito.eq(Events.ACCOUNT_CREATED), Mockito.eq(testAccount.getId()));

        Mockito.when(userDao.getAll()).thenReturn(accounts);
        Assert.assertNotNull("An account was created with an existing mail adress.",
                exceptionThrownBy(() -> accountService.create(accounts.get(0))));
    }
}
