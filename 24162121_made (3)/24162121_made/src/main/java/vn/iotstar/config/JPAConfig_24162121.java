package vn.iotstar.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAConfig_24162121 {
    private static final EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("dataSource");

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}
