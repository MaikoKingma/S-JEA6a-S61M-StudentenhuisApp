package Service;

import DAO.IAccountDao;
import JMS.JMSBrokerGateway;
import JMS.Message.Events;
import Models.Account;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class AccountService {

    @Inject
    private IAccountDao userDao;
    @Inject
    private JMSBrokerGateway jmsBroker;

    public Account create(Account account) {
        List<Account> currentAccounts = userDao.getAll();
        for(Account a : currentAccounts) {
            if (a.getMail().equals(account.getMail()));
            throw new NullPointerException();
        }

        userDao.create(account);
        jmsBroker.sendMessage("New Account Created", Events.ACCOUNT_CREATED, account.getId());
        return account;
    }
}
