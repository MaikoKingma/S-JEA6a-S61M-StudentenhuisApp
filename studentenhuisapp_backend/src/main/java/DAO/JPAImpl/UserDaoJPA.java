package DAO.JPAImpl;

import DAO.DaoFacade;
import DAO.IUserDao;
import Models.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

@Stateless
@Default
public class UserDaoJPA extends DaoFacade<User> implements IUserDao {

    public UserDaoJPA() {
        super(User.class);
    }
}
