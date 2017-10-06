package Service;

import DAO.IGroupDao;
import JMS.JMSBrokerGateway;
import JMS.Message.Events;
import Models.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

//Note: always check if events are send to the JMS broker
@RunWith(MockitoJUnitRunner.class)
public class GroupServiceTest {

    @InjectMocks
    private GroupService groupService;

    @Mock
    private IGroupDao groupDao;
    @Mock
    private AccountService accountService;
    @Mock
    private JMSBrokerGateway jmsBroker;

    public GroupServiceTest() { }

    @Before
    public void setUp() throws Exception {
        //Mockito.doNothing().when(jmsBroker).sendMessage(Mockito.anyString(), Mockito.any(Events.class));
        //Mockito.doNothing().when(jmsBroker).sendMessage(Mockito.anyString(), Mockito.any(Events.class), Mockito.anyLong());
        Mockito.doNothing().when(jmsBroker).sendMessage(Mockito.anyString(), Mockito.any(Events.class), Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    public void createGroupTest() throws Exception {
        final Account account = new Account("Maiko", "maiko@mail.nl");
        final Group testGroup = new Group("Studentenhuis");
        final Account accountWithGroup = account;
        accountWithGroup.addGroup(testGroup);
        Mockito.when(accountService.findById(account.getId()))
                .thenReturn(account);
        Mockito.when(accountService.edit(accountWithGroup))
                .thenReturn(accountWithGroup);
        Mockito.when(groupDao.create(testGroup))
                .thenReturn(testGroup);
        Assert.assertEquals("Group was not created",
                testGroup,
                groupService.create(testGroup, account.getId()));
        Mockito.verify(jmsBroker)
                .sendMessage(Mockito.anyString(), Mockito.eq(Events.GROUP_CREATED), Mockito.eq(testGroup.getId()), Mockito.eq(account.getId()));
    }

    @Test
    public void findByIdTest() throws Exception {
        final Group testGroup = new Group("Studentenhuis");

        Mockito.when(groupDao.findById(testGroup.getId()))
                .thenReturn(testGroup);

        Assert.assertEquals("Did not return the right product.",
                testGroup,
                groupDao.findById(testGroup.getId()));
    }
}
