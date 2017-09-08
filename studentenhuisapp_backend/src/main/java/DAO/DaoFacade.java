package DAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public abstract class DaoFacade<T> {
    private final Class<T> entityClass;

    @PersistenceContext
    protected EntityManager em;

    public DaoFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void create(T entity) {
        em.persist(entity);
    }

    public void edit(T entity) {
        em.merge(entity);
    }

    public void remove(T entity) {
        em.remove(em.merge(entity));
    }

    public T findById(Object id) {
        return em.find(entityClass, id);
    }

    public List<T> getAll() {
        return em.createQuery("select t from " + entityClass.getSimpleName() + " t").getResultList();
    }
}