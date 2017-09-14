package Service;

import DAO.IUserDao;
import Models.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private IUserDao userDao;

    @Test
    public void createUserTest() throws Exception {
        final User testUser = new User("Maiko", "maiko@mail.nl");
        Assert.assertEquals(testUser, userService.create(testUser));
    }
}
