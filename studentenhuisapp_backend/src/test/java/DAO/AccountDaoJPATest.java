package DAO;

import DAO.JPAImpl.AccountDaoJPA;
import Models.Account;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;

@RunWith(MockitoJUnitRunner.class)
public class AccountDaoJPATest {

    @InjectMocks
    private AccountDaoJPA productDao;

    @Mock
    private EntityManager em;

    public AccountDaoJPATest() { }

    @Test
    public void createUserTest() throws Exception {
        System.out.println("Test create user");
        //Create a user and test if the values are correct
        final Account correctAccount = new Account("Maiko", "maiko@mail.nl");
        Assert.assertEquals(correctAccount, productDao.create(correctAccount));
        Assert.assertTrue(correctAccount.isActive());

//        System.out.println("Test duplicate user");
//        //Check if duplicate mail adresses are allowed.
//        final Account duplicateUser = new Account("Maiko", "maiko@mail.nl");
//        Assert.assertEquals(null, duplicateUser);

        System.out.println("Test empty name");
        try {
            productDao.create(new Account("", "pim@mail.nl"));
            Assert.fail("Empty name should not be accepted.");
        } catch (NullPointerException e) { }

        System.out.println("Test empty mail");
        try {
            productDao.create(new Account("Pim", ""));
            Assert.fail("Empty mail should not be accepted.");
        } catch (NullPointerException e) { }

        System.out.println("Test null name");
        try {
            productDao.create(new Account(null, "pim@mail.nl"));
            Assert.fail("Null name should not be accepted.");
        } catch (NullPointerException e) { }

        System.out.println("Test null mail");
        try {
            productDao.create(new Account("Pim", null));
            Assert.fail("Null mail should not be accepted.");
        } catch (NullPointerException e) { }

        //ToDo test duplicate name
    }
}
