package DAO;

import DAO.JPAImpl.GroupDaoJPA;
import Models.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;

import static com.github.stefanbirkner.fishbowl.Fishbowl.exceptionThrownBy;

@RunWith(MockitoJUnitRunner.class)
public class GroupDaoJPATest {

    @InjectMocks
    private GroupDaoJPA groupDao;

    @Mock
    private EntityManager em;

    public GroupDaoJPATest() { }

    @Test
    public void createAccountTest() throws Exception {
        final Account account = new Account("Maiko", "maiko@mail.nl");
        final Group correctGroup = new Group("Studentenhuis", account);
        Assert.assertEquals("Group not created.",
                correctGroup,
                groupDao.create(correctGroup));

        Assert.assertNotNull("Empty name was accepted.",
                exceptionThrownBy(() -> groupDao.create(new Group("", account))));

        Assert.assertNotNull("Null name was accepted",
                exceptionThrownBy(() -> groupDao.create(new Group(null, account))));

        Assert.assertNotNull("Null account was accepted",
                exceptionThrownBy(() -> groupDao.create(new Group("Maiko", null))));
    }
}
