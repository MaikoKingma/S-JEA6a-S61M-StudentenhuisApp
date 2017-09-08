package DAO;

import java.util.List;

public interface IDaoBase<T> {
    List<T> getAll();
    T findById(Object id);
    void create(T entity);
    void edit(T entity);
    void remove(T entity);
}
