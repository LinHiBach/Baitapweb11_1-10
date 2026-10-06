package vn.hcmute.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import vn.hcmute.config.JpaConfig_24110165;
import vn.hcmute.dao.IOrderDao_24110165;
import vn.hcmute.entity.Order_24110165;

public class OrderDaoImpl_24110165 implements IOrderDao_24110165 {

    @Override
    public Order_24110165 insert(Order_24110165 order) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(order);
            trans.commit();
            return order;
        } catch (Exception e) {
            if (trans != null && trans.isActive()) {
                trans.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            enma.close();
        }
    }

    @Override
    public Order_24110165 findById(int orderId) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            Order_24110165 order = enma.find(Order_24110165.class, orderId);
            if (order != null) {
                // Kích hoạt nạp chi tiết đơn hàng (lazy load details)
                order.getDetails().size();
            }
            return order;
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Order_24110165> findByUsername(String username) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            String jpql = "SELECT o FROM Order_24110165 o WHERE o.username = :username ORDER BY o.createdAt DESC";
            TypedQuery<Order_24110165> query = enma.createQuery(jpql, Order_24110165.class);
            query.setParameter("username", username);
            List<Order_24110165> list = query.getResultList();
            for (Order_24110165 o : list) {
                o.getDetails().size();
            }
            return list;
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Order_24110165> findByUsernameAndStatus(String username, int status) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            String jpql = "SELECT o FROM Order_24110165 o WHERE o.username = :username AND o.status = :status ORDER BY o.createdAt DESC";
            TypedQuery<Order_24110165> query = enma.createQuery(jpql, Order_24110165.class);
            query.setParameter("username", username);
            query.setParameter("status", status);
            List<Order_24110165> list = query.getResultList();
            for (Order_24110165 o : list) {
                o.getDetails().size();
            }
            return list;
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Order_24110165> findAll() {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            TypedQuery<Order_24110165> query = enma.createNamedQuery("Order_24110165.findAll", Order_24110165.class);
            List<Order_24110165> list = query.getResultList();
            for (Order_24110165 o : list) {
                o.getDetails().size();
            }
            return list;
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Order_24110165> findByStatus(int status) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            String jpql = "SELECT o FROM Order_24110165 o WHERE o.status = :status ORDER BY o.createdAt DESC";
            TypedQuery<Order_24110165> query = enma.createQuery(jpql, Order_24110165.class);
            query.setParameter("status", status);
            List<Order_24110165> list = query.getResultList();
            for (Order_24110165 o : list) {
                o.getDetails().size();
            }
            return list;
        } finally {
            enma.close();
        }
    }

    @Override
    public void update(Order_24110165 order) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(order);
            trans.commit();
        } catch (Exception e) {
            if (trans != null && trans.isActive()) {
                trans.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            enma.close();
        }
    }
}
