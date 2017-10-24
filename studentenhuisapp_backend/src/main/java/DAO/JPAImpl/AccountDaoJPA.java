package DAO.JPAImpl;

import DAO.IAccountDao;
import Models.Account;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;
import java.util.List;

@Stateless
@Default
public class AccountDaoJPA implements IAccountDao {

    @PersistenceContext
    private EntityManager em;

    public AccountDaoJPA() { }

    @Override
    public Account create(Account account) {
        if (account.getFullName().equals("") ||
                account.getGoogleId().equals("")) {
            throw new NullPointerException();
        }
        em.persist(account);
        return account;
    }

    @Override
    public Account edit(Account entity) {
        if (em.find(Account.class, entity.getId()).isActive()) {
            return em.merge(entity);
        }
        throw new NullPointerException();
    }

    @Override
    public List<Account> getAll() {
        return em.createQuery("select t from " + Account.class.getSimpleName() + " t").getResultList();
    }

    @Override
    public Account findById(long id) {
        return em.find(Account.class, id);
    }

    @Override
    public Account findByGoogleId(String googleId) {
        Query q = em.createNamedQuery("accountdao.findByGoogleId");
        q.setParameter("googleId", googleId);

        Account account;
        try {
            account = (Account)q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

        if (account.isActive()) {
            return account;
        }
        throw new NullPointerException();
    }
}
