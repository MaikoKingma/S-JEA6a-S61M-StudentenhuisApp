package DAO;

import DAO.JPAImpl.AccountDaoJPA;
import Models.Account;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;

import static com.github.stefanbirkner.fishbowl.Fishbowl.exceptionThrownBy;

@RunWith(MockitoJUnitRunner.class)
public class AccountDaoJPATest {

    @InjectMocks
    private AccountDaoJPA productDao;

    @Mock
    private EntityManager em;

    public AccountDaoJPATest() { }

    @Test
    public void createUserTest() throws Exception {
        final Account correctAccount = new Account("Maiko", "maiko@mail.nl");
        Assert.assertEquals("User not created.",
                correctAccount,
                productDao.create(correctAccount));
        Assert.assertTrue("New user is not active.",
                correctAccount.isActive());

        Assert.assertNotNull("Empty name was accepted.",
                exceptionThrownBy(() -> productDao.create(new Account("", "pim@mail.nl"))));

        Assert.assertNotNull("Empty mail was accepted",
                exceptionThrownBy(() -> productDao.create(new Account("Pim", ""))));

        Assert.assertNotNull("Null name was accepted",
                exceptionThrownBy(() -> productDao.create(new Account(null, "pim@mail.nl"))));

        Assert.assertNotNull("Null mail was accepted",
                exceptionThrownBy(() -> productDao.create(new Account("Pim", null))));
    }
}
