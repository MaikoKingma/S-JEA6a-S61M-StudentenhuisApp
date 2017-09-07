package Utils;

import javax.persistence.EntityManager;
import java.sql.SQLException;

public class DatabaseCleaner {
    private final EntityManager em;

    public DatabaseCleaner(EntityManager entityManager) {
        em = entityManager;
    }

    public void clean() throws SQLException {
        em.getTransaction().begin();

        //ToDO add models

        em.getTransaction().commit();
        em.close();
    }

    private void deleteEntity(Class<?> entity) {
        em.createQuery("delete from " + em.getMetamodel().entity(entity).getName()).executeUpdate();
    }
}
