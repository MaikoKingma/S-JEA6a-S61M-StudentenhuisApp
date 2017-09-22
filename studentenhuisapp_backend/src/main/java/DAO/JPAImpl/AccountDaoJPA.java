package DAO.JPAImpl;

import DAO.IAccountDao;
import Models.Account;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;

@Stateless
@Default
public class AccountDaoJPA implements IAccountDao {

    @PersistenceContext(unitName = "StudentenhuisappPU")
    private EntityManager em;

    public AccountDaoJPA() { }

    @Override
    public Account create(Account account) {
        if (account.getFullName().equals("") ||
                account.getMail().equals("")) {
            throw new NullPointerException();
        }
        account.setActive(true);
        em.persist(account);
        return account;
    }

    @Override
    public Account edit(Account entity) {
        return em.merge(entity);
    }
}
