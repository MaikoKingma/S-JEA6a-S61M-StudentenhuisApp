package DAO.JPAImpl;

import DAO.IGroupDao;
import Models.Group;

import javax.persistence.*;
import java.util.List;

public class GroupDaoJPA implements IGroupDao {

    @PersistenceContext(unitName = "StudentenhuisappPU")
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
    public List<Group> getAll() {
        return em.createQuery("select t from " + Group.class.getSimpleName() + " t").getResultList();
    }
}
