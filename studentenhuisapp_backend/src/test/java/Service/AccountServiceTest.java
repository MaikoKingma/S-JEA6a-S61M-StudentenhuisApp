package Service;

import DAO.IAccountDao;
import JMS.JMSBrokerGateway;
import JMS.Message.Events;
import Models.Account;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.URI;
import java.util.*;

import static com.github.stefanbirkner.fishbowl.Fishbowl.exceptionThrownBy;

//Note: always check if events are send to the JMS broker
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    List<Account> accounts;

    @Mock
    private IAccountDao userDao;
    @Mock
    private JMSBrokerGateway jmsBroker;

    @Mock
    private OAuthService oAuthService;

    @InjectMocks
    private AccountService accountService;

    public AccountServiceTest() {
        accounts = new ArrayList<Account>() {{
            add(new Account("Maiko", "47312904"));
            add(new Account("Pim", "3411235"));
            add(new Account("Loek", "1261341"));
        }};
    }

    @Before
    public void setUp() throws Exception {
        //Mockito.doNothing().when(jmsBroker).sendMessage(Mockito.anyString(), Mockito.any(Events.class));
        Mockito.doNothing().when(jmsBroker).sendMessage(Mockito.anyString(), Mockito.any(Events.class), Mockito.anyLong());
        //Mockito.doNothing().when(jmsBroker).sendMessage(Mockito.anyString(), Mockito.any(Events.class), Mockito.anyLong(), Mockito.anyLong());
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
    public void findByIdTest() throws Exception {
        final Account testAccount = new Account("Maiko", "754221452");

        Mockito.when(userDao.findById(testAccount.getId()))
                .thenReturn(testAccount);

        Assert.assertEquals("Did not return the right product.",
                testAccount,
                accountService.findById(testAccount.getId()));
    }

    @Test
    public void requestLoginTest() throws Exception {
        URI uri = new URI("www.google.nl");
        Mockito.when(oAuthService.getAuthorizationUri())
                .thenReturn(uri);

        URI response = accountService.requestLogin();

        Assert.assertEquals("Did not return the right uri.",
                uri,
                response);
    }

    @Test
    public void findByGoogleIdTest() throws Exception {
        final Account testAccount = new Account("Maiko", "754221452");

        Mockito.when(userDao.findByGoogleId(testAccount.getGoogleId()))
                .thenReturn(testAccount);

        Assert.assertEquals("Did not return the right product.",
                testAccount,
                accountService.findByGoogleId(testAccount.getGoogleId()));
    }
}
