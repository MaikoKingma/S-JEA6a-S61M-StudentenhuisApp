package DAO.JPAImpl;

import DAO.IAccountDao;
import Models.Account;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;
import java.util.List;

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
    public Account findByMail(String mail) {
        Query q = em.createNamedQuery("accountdao.findByMail");
        q.setParameter("mail", mail);
        final Account foundAccount = (Account) q.getSingleResult();
        if (foundAccount.isActive()) {
            return foundAccount;
        }
        throw new NullPointerException();
    }

    @Override
    public Account findById(long id) {
        return em.find(Account.class, id);
    }
}
