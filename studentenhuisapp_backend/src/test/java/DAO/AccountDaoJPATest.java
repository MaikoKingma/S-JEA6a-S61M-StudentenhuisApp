package DAO;

import DAO.JPAImpl.AccountDaoJPA;
import Models.Account;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.Query;

import java.util.*;

import static com.github.stefanbirkner.fishbowl.Fishbowl.exceptionThrownBy;

@RunWith(MockitoJUnitRunner.class)
public class AccountDaoJPATest {

    @InjectMocks
    private AccountDaoJPA productDao;

    @Mock
    private EntityManager em;

    List<Account> accounts;

    public AccountDaoJPATest() {
        accounts = new ArrayList<Account>() {{
            add(new Account("Maiko", "maiko@mail.nl"));
            add(new Account("Pim", "pim@mail.nl"));
            add(new Account("Loek", "loek@mail.nl"));
        }};
        accounts.get(0).setId(1);
        accounts.get(0).setActive(true);
        accounts.get(1).setId(2);
        accounts.get(1).setActive(true);
        accounts.get(2).setId(3);
        accounts.get(2).setActive(false);
    }

    @Test
    public void createAccountTest() throws Exception {
        final Account correctAccount = new Account("Maiko", "maiko@mail.nl");
        Assert.assertEquals("User not created.",
                correctAccount,
                productDao.create(correctAccount));

        Assert.assertNotNull("Empty name was accepted.",
                exceptionThrownBy(() -> productDao.create(new Account("", "pim@mail.nl"))));

        Assert.assertNotNull("Empty mail was accepted",
                exceptionThrownBy(() -> productDao.create(new Account("Pim", ""))));

        Assert.assertNotNull("Null name was accepted",
                exceptionThrownBy(() -> productDao.create(new Account(null, "pim@mail.nl"))));

        Assert.assertNotNull("Null mail was accepted",
                exceptionThrownBy(() -> productDao.create(new Account("Pim", null))));
    }

    @Test
    public void getAllAccountsTest() throws Exception {
        final Query mockQuery = Mockito.mock(Query.class);
        final String query = "select t from Account t";
        Mockito.when(mockQuery.getResultList())
                .thenReturn(accounts);
        Mockito.when(em.createQuery(query))
                .thenReturn(mockQuery);

        final List<Account> newAccounts = productDao.getAll();
        Assert.assertEquals("Get All Accounts did not return the correct accounts",
                accounts.size(),
                newAccounts.size());
    }

    @Test
    public void editAccountTest() throws Exception {
        Mockito.when(em.merge(accounts.get(0))).thenReturn(accounts.get(0));
        Mockito.when(em.find(Account.class, accounts.get(0).getId())).thenReturn(accounts.get(0));
        Assert.assertEquals("Account was not merged.",
            accounts.get(0),
            productDao.edit(accounts.get(0)));
        Mockito.verify(em).merge(accounts.get(0));
    }

    @Test
    public void findByMailAccountTest() throws Exception {
        Query mockedQuery = Mockito.mock(Query.class);
        Mockito.when(em.createNamedQuery("accountdao.findByMail")).thenReturn(mockedQuery);
        Mockito.when(mockedQuery.getSingleResult()).thenReturn(accounts.get(0));

        Assert.assertEquals("Wrong account was returned.",
                accounts.get(0),
                productDao.findByMail(accounts.get(0).getMail()));
        Mockito.verify(mockedQuery).setParameter("mail", accounts.get(0).getMail());

        Mockito.when(mockedQuery.getSingleResult()).thenReturn(accounts.get(2));

        Assert.assertNotNull("Inactive account was returned.",
                exceptionThrownBy(() -> productDao.findByMail(accounts.get(2).getMail())));
    }
}
