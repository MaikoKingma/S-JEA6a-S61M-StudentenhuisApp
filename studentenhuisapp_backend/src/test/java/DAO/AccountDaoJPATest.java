package DAO;

import DAO.JPAImpl.AccountDaoJPA;
import Models.Account;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.*;

import java.util.*;

import static com.github.stefanbirkner.fishbowl.Fishbowl.exceptionThrownBy;

@RunWith(MockitoJUnitRunner.class)
public class AccountDaoJPATest {

    @InjectMocks
    private AccountDaoJPA accountDao;

    @Mock
    private EntityManager em;

    List<Account> accounts;

    public AccountDaoJPATest() {
        accounts = new ArrayList<Account>() {{
            add(new Account("Maiko", "47312904"));
            add(new Account("Pim", "3411235"));
            add(new Account("Loek", "1261341"));
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
        final Account correctAccount = new Account("Maiko", "754221452");
        Assert.assertEquals("User not created.",
                correctAccount,
                accountDao.create(correctAccount));

        Assert.assertNotNull("Empty name was accepted.",
                exceptionThrownBy(() -> accountDao.create(new Account("", "754221452"))));

        Assert.assertNotNull("Empty googleId was accepted",
                exceptionThrownBy(() -> accountDao.create(new Account("Pim", ""))));

        Assert.assertNotNull("Null name was accepted",
                exceptionThrownBy(() -> accountDao.create(new Account(null, "754221452"))));

        Assert.assertNotNull("Null googleId was accepted",
                exceptionThrownBy(() -> accountDao.create(new Account("Pim", null))));
    }

    @Test
    public void getAllAccountsTest() throws Exception {
        final Query mockQuery = Mockito.mock(Query.class);
        final String query = "select t from Account t";
        Mockito.when(mockQuery.getResultList())
                .thenReturn(accounts);
        Mockito.when(em.createQuery(query))
                .thenReturn(mockQuery);

        final List<Account> newAccounts = accountDao.getAll();
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
            accountDao.edit(accounts.get(0)));
        Mockito.verify(em).merge(accounts.get(0));
    }

    @Test
    public void findByIdTest() throws Exception {
        final Account account = new Account("Maiko", "754221452");
        Mockito.when(em.find(Account.class, account.getId()))
                .thenReturn(account);

        Assert.assertEquals(account, accountDao.findById(account.getId()));
    }

    @Test
    public void findByGoogleIdTest() throws Exception {
        Query mockedQuery = Mockito.spy(Query.class);
        Mockito.when(em.createNamedQuery("accountdao.findByGoogleId")).thenReturn(mockedQuery);
        Mockito.when(mockedQuery.getSingleResult()).thenReturn(accounts.get(0));

        Assert.assertEquals("Wrong account was returned.",
                accounts.get(0),
                accountDao.findByGoogleId(accounts.get(0).getGoogleId()));
        Mockito.verify(mockedQuery).setParameter("googleId", accounts.get(0).getGoogleId());

        Mockito.when(mockedQuery.getSingleResult()).thenReturn(accounts.get(2));
        Assert.assertNotNull("Inactive account was returned.",
                exceptionThrownBy(() -> accountDao.findByGoogleId(accounts.get(2).getGoogleId())));
    }

    @Test
    public void findByGoogleIdNullTest() throws Exception {
        Query mockedQuery = Mockito.spy(Query.class);
        Mockito.when(em.createNamedQuery("accountdao.findByGoogleId")).thenReturn(mockedQuery);
        Mockito.when(mockedQuery.getSingleResult()).thenThrow(new NoResultException());
        Assert.assertNull("Method did not return null", accountDao.findByGoogleId("42809751"));
    }
}
