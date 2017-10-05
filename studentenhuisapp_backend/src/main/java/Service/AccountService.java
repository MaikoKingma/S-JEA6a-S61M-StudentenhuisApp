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
    private IAccountDao accountDao;
    @Inject
    private JMSBrokerGateway jmsBroker;

    public Account create(Account account) {
        List<Account> currentAccounts = accountDao.getAll();
        for(Account a : currentAccounts) {
            if (a.getMail().equals(account.getMail())) {
                throw new NullPointerException();
            }
        }

        account.setActive(true);
        Account newAccount = accountDao.create(account);
        jmsBroker.sendMessage("New Account Created", Events.ACCOUNT_CREATED, newAccount.getId());
        return newAccount;
    }

    public Account edit(Account account) {
        account.setActive(true);
        Account newAccount = accountDao.edit(account);
        jmsBroker.sendMessage("Account modified", Events.ACCOUNT_MODIFIED, newAccount.getId());
        return newAccount;
    }

    public Account login(String mail) {
        Account account = accountDao.findByMail(mail);
        jmsBroker.sendMessage("User logged in", Events.ACCOUNT_LOGGED_IN, account.getId());
        return account;
    }

    public Account findById(long id) {
        return accountDao.findById(id);
    }
}
