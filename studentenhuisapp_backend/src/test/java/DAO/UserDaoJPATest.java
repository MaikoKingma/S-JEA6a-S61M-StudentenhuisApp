package DAO;

import DAO.JPAImpl.UserDaoJPA;
import Models.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoJPATest {

    @InjectMocks
    private UserDaoJPA productDao;

    @Mock
    private EntityManager em;

    public UserDaoJPATest() { }

    @Test
    public void createUserTest() throws Exception {
        System.out.println("Test create user");
        //Create a user and test if the values are correct
        final User correctUser = new User("Maiko", "maiko@mail.nl");
        Assert.assertEquals(correctUser, productDao.create(correctUser));
        Assert.assertTrue(correctUser.isActive());

//        System.out.println("Test duplicate user");
//        //Check if duplicate mail adresses are allowed.
//        final User duplicateUser = new User("Maiko", "maiko@mail.nl");
//        Assert.assertEquals(null, duplicateUser);

        System.out.println("Test empty name");
        try {
            productDao.create(new User("", "pim@mail.nl"));
            Assert.fail("Empty name should not be accepted.");
        } catch (NullPointerException e) { }

        System.out.println("Test empty mail");
        try {
            productDao.create(new User("Pim", ""));
            Assert.fail("Empty mail should not be accepted.");
        } catch (NullPointerException e) { }

        System.out.println("Test null name");
        try {
            productDao.create(new User(null, "pim@mail.nl"));
            Assert.fail("Null name should not be accepted.");
        } catch (NullPointerException e) { }

        System.out.println("Test null mail");
        try {
            productDao.create(new User("Pim", null));
            Assert.fail("Null mail should not be accepted.");
        } catch (NullPointerException e) { }

        //ToDo test duplicate name
    }
}
