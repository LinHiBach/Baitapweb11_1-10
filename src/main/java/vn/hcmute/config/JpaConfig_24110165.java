package vn.hcmute.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaConfig_24110165 {
    private static EntityManagerFactory factory;

    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (factory == null || !factory.isOpen()) {
            try {
                factory = Persistence.createEntityManagerFactory("dataSource");
            } catch (Exception e) {
                System.err.println("❌ LỖI KHỞI TẠO ENTITY MANAGER FACTORY: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }
        return factory;
    }

    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    public static synchronized void close() {
        if (factory != null && factory.isOpen()) {
            factory.close();
            factory = null;
        }
    }
}
