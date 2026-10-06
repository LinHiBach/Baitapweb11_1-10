package vn.hcmute.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import vn.hcmute.config.JpaConfig_24110165;
import vn.hcmute.dao.ICategoryDao_24110165;
import vn.hcmute.entity.Category_24110165;

public class CategoryDaoImpl_24110165 implements ICategoryDao_24110165 {

    @Override
    public void insert(Category_24110165 cate) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(cate);
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
    public void update(Category_24110165 cate) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(cate);
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
    public void delete(int id) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            Category_24110165 cate = enma.find(Category_24110165.class, id);
            if (cate != null) {
                enma.remove(cate);
            }
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
    public Category_24110165 findById(int id) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            return enma.find(Category_24110165.class, id);
        } finally {
            enma.close();
        }
    }

    @Override
    public Category_24110165 findByCategoryname(String name) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            String jpql = "SELECT c FROM Category_24110165 c WHERE c.categoryname = :catename";
            TypedQuery<Category_24110165> query = enma.createQuery(jpql, Category_24110165.class);
            query.setParameter("catename", name);
            List<Category_24110165> list = query.getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Category_24110165> findAll() {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            TypedQuery<Category_24110165> query = enma.createNamedQuery("Category_24110165.findAll", Category_24110165.class);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Category_24110165> searchByName(String keyword) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            String jpql = "SELECT c FROM Category_24110165 c WHERE c.categoryname LIKE :keyword";
            TypedQuery<Category_24110165> query = enma.createQuery(jpql, Category_24110165.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Category_24110165> findAll(int page, int pagesize) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            TypedQuery<Category_24110165> query = enma.createNamedQuery("Category_24110165.findAll", Category_24110165.class);
            int first = page > 0 ? (page - 1) * pagesize : 0;
            query.setFirstResult(first);
            query.setMaxResults(pagesize);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public int count() {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            String jpql = "SELECT COUNT(c) FROM Category_24110165 c";
            TypedQuery<Long> query = enma.createQuery(jpql, Long.class);
            return query.getSingleResult().intValue();
        } finally {
            enma.close();
        }
    }

    @Override
    public void edit(Category_24110165 category) {
        update(category);
    }

    @Override
    public Category_24110165 get(int id) {
        return findById(id);
    }

    @Override
    public List<Category_24110165> getAll() {
        return findAll();
    }

    @Override
    public List<Category_24110165> search(String keyword) {
        return searchByName(keyword);
    }
}
