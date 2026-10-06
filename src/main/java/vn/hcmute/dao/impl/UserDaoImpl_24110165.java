package vn.hcmute.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import vn.hcmute.config.JpaConfig_24110165;
import vn.hcmute.dao.IUserDao_24110165;
import vn.hcmute.entity.User_24110165;

public class UserDaoImpl_24110165 implements IUserDao_24110165 {

    @Override
    public User_24110165 findByUsername(String username) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE u.username = :username";
            TypedQuery<User_24110165> query = enma.createQuery(jpql, User_24110165.class);
            query.setParameter("username", username);
            List<User_24110165> list = query.getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            enma.close();
        }
    }

    @Override
    public User_24110165 findByEmail(String email) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE u.email = :email";
            TypedQuery<User_24110165> query = enma.createQuery(jpql, User_24110165.class);
            query.setParameter("email", email);
            List<User_24110165> list = query.getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            enma.close();
        }
    }

    @Override
    public User_24110165 findByCodeAndEmail(String code, String email) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE u.code = :code AND u.email = :email";
            TypedQuery<User_24110165> query = enma.createQuery(jpql, User_24110165.class);
            query.setParameter("code", code);
            query.setParameter("email", email);
            List<User_24110165> list = query.getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            enma.close();
        }
    }

    @Override
    public List<User_24110165> findAll() {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            TypedQuery<User_24110165> query = enma.createNamedQuery("User.findAll", User_24110165.class);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public void insert(User_24110165 user) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(user);
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

    @Override
    public void update(User_24110165 user) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(user);
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

    @Override
    public boolean checkExistEmail(String email) {
        return findByEmail(email) != null;
    }

    @Override
    public boolean checkExistUsername(String username) {
        return findByUsername(username) != null;
    }

    @Override
    public boolean checkExistPhone(String phone) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE u.phone = :phone";
            TypedQuery<User_24110165> query = enma.createQuery(jpql, User_24110165.class);
            query.setParameter("phone", phone);
            List<User_24110165> list = query.getResultList();
            return !list.isEmpty();
        } finally {
            enma.close();
        }
    }

    @Override
    public User_24110165 get(String username) {
        return findByUsername(username);
    }
}
