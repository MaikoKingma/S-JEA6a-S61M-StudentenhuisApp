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
        final Group correctGroup = new Group("Studentenhuis");
        Assert.assertEquals("Group not created.",
                correctGroup,
                groupDao.create(correctGroup));

        Assert.assertNotNull("Empty name was accepted.",
                exceptionThrownBy(() -> groupDao.create(new Group(""))));

        Assert.assertNotNull("Null name was accepted",
                exceptionThrownBy(() -> groupDao.create(new Group(null))));
    }

    @Test
    public void findByIdTest() throws Exception {
        final Group group = new Group("Studentenhuis");
        Mockito.when(em.find(Group.class, group.getId()))
                .thenReturn(group);

        Assert.assertEquals(group, groupDao.findById(group.getId()));
    }
}
