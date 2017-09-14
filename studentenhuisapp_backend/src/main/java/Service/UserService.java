package Service;

import DAO.IUserDao;
import JMS.JMSBrokerGateway;
import JMS.Message.Events;
import Models.User;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UserService {

    @Inject
    private IUserDao userDao;
    @Inject
    private JMSBrokerGateway jmsBroker;

    public User create(User user) {
        userDao.create(user);
        jmsBroker.sendMessage("New Account Created", Events.ACCOUNT_CREATED, user.getId());
        return user;
    }
}
