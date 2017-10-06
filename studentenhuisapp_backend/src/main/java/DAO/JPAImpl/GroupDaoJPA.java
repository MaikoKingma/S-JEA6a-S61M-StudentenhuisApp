package DAO.JPAImpl;

import DAO.IGroupDao;
import Models.Group;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;

@Stateless
@Default
public class GroupDaoJPA implements IGroupDao {

    @PersistenceContext
    private EntityManager em;

    public GroupDaoJPA() { }

    @Override
    public Group create(Group group) {
        if (group.getName().equals("")) {
            throw new NullPointerException();
        }
        em.persist(group);
        return group;
    }

    @Override
    public Group findById(long id) {
        return em.find(Group.class, id);
    }
}
