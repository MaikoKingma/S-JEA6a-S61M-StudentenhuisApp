package DAO.JPAImpl;

import DAO.IUserDao;
import Models.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;

@Stateless
@Default
public class UserDaoJPA implements IUserDao {

    @PersistenceContext(unitName = "ProjectPub_StockPU")
    private EntityManager em;

    public UserDaoJPA() { }

    @Override
    public User create(User user) {
        if (user.getFullName().equals("") ||
                user.getMail().equals("")) {
            throw new NullPointerException();
        }
        em.persist(user);
        return user;
    }

    @Override
    public User edit(User entity) {
        //ToDo User Story: Modify Account
        //https://trello.com/c/JKkOgA1P/5-modify-account
        throw new NotImplementedException();
    }
}
