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

    @Before
    public void setUp() throws Exception {
        //Mockito.doNothing().when(jmsBroker).sendMessage(Mockito.anyString(), Mockito.any(Events.class));
        Mockito.doNothing().when(jmsBroker).sendMessage(Mockito.anyString(), Mockito.any(Events.class), Mockito.anyLong());
        //Mockito.doNothing().when(jmsBroker).sendMessage(Mockito.anyString(), Mockito.any(Events.class), Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    public void createAccountTest() throws Exception {
        final Account testAccount = new Account("Maiko", "maiko@mail.nl");
        Mockito.when(userDao.create(testAccount)).thenReturn(testAccount);
        Assert.assertEquals("User was not created",
                testAccount,
                accountService.create(testAccount));
        Assert.assertTrue("New user is not active.",
                testAccount.isActive());
        Mockito.verify(jmsBroker)
                .sendMessage(Mockito.anyString(), Mockito.eq(Events.ACCOUNT_CREATED), Mockito.eq(testAccount.getId()));

        Mockito.when(userDao.getAll()).thenReturn(accounts);
        Assert.assertNotNull("An account was created with an existing mail adress.",
                exceptionThrownBy(() -> accountService.create(accounts.get(0))));
    }

    @Test
    public void editAccountTest() throws Exception {
        Mockito.when(userDao.edit(accounts.get(0))).thenReturn(accounts.get(0));
        Assert.assertEquals("Account was not edited.",
                accounts.get(0),
                accountService.edit(accounts.get(0)));
        Mockito.verify(jmsBroker)
                .sendMessage(Mockito.anyString(), Mockito.eq(Events.ACCOUNT_MODIFIED), Mockito.eq(accounts.get(0).getId()));
    }

    @Test
    public void loginAccountTest() throws Exception {
        Mockito.when(userDao.findByMail(accounts.get(0).getMail())).thenReturn(accounts.get(0));
        Assert.assertEquals("Wrong account returned.",
                accounts.get(0),
                accountService.login(accounts.get(0).getMail()));
        Mockito.verify(jmsBroker)
                .sendMessage(Mockito.anyString(), Mockito.eq(Events.ACCOUNT_LOGGED_IN), Mockito.eq(accounts.get(0).getId()));
    }
}
