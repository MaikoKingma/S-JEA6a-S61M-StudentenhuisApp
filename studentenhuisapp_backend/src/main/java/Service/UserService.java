package Service;

import DAO.IUserDao;
import Models.User;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UserService {

    @Inject
    private IUserDao userDao;

    public User create(User user) {
        userDao.create(user);
        return user;
    }
}
