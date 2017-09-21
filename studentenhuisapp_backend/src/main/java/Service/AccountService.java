package Service;

import DAO.IAccountDao;
import JMS.JMSBrokerGateway;
import JMS.Message.Events;
import Models.Account;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AccountService {

    @Inject
    private IAccountDao userDao;
    @Inject
    private JMSBrokerGateway jmsBroker;

    public Account create(Account account) {
        userDao.create(account);
        jmsBroker.sendMessage("New Account Created", Events.ACCOUNT_CREATED, account.getId());
        return account;
    }
}
