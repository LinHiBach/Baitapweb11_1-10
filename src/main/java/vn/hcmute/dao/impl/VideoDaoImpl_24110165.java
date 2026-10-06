package vn.hcmute.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import vn.hcmute.config.JpaConfig_24110165;
import vn.hcmute.dao.IVideoDao_24110165;
import vn.hcmute.entity.Video_24110165;

public class VideoDaoImpl_24110165 implements IVideoDao_24110165 {

    @Override
    public void insert(Video_24110165 video) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(video);
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
    public void update(Video_24110165 video) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(video);
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
    public void delete(String id) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            Video_24110165 video = enma.find(Video_24110165.class, id);
            if (video != null) {
                enma.remove(video);
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
    public Video_24110165 findById(String id) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            List<Video_24110165> videos = enma.createQuery(
                "SELECT v FROM Video v LEFT JOIN FETCH v.category WHERE v.videoId = :id", Video_24110165.class)
                .setParameter("id", id).getResultList();
            return videos.isEmpty() ? null : videos.get(0);
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Video_24110165> findAll() {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            TypedQuery<Video_24110165> query = enma.createQuery(
                "SELECT v FROM Video v LEFT JOIN FETCH v.category ORDER BY v.videoId", Video_24110165.class);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Video_24110165> findByTitle(String title) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            String jpql = "SELECT v FROM Video v WHERE v.title LIKE :title";
            TypedQuery<Video_24110165> query = enma.createQuery(jpql, Video_24110165.class);
            query.setParameter("title", "%" + title + "%");
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Video_24110165> findAll(int page, int pagesize) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            TypedQuery<Video_24110165> query = enma.createQuery(
                "SELECT v FROM Video v LEFT JOIN FETCH v.category ORDER BY v.videoId", Video_24110165.class);
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
            String jpql = "SELECT COUNT(v) FROM Video v";
            TypedQuery<Long> query = enma.createQuery(jpql, Long.class);
            return query.getSingleResult().intValue();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Video_24110165> findByCategoryId(int categoryId, int page, int pagesize) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            TypedQuery<Video_24110165> query = enma.createQuery(
                "SELECT v FROM Video v WHERE v.category.categoryId = :categoryId ORDER BY v.videoId", Video_24110165.class);
            query.setParameter("categoryId", categoryId);
            query.setFirstResult(Math.max(0, (page - 1) * pagesize));
            query.setMaxResults(pagesize);
            return query.getResultList();
        } finally { enma.close(); }
    }

    @Override
    public int countByCategoryId(int categoryId) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            return enma.createQuery("SELECT COUNT(v) FROM Video v WHERE v.category.categoryId = :categoryId", Long.class)
                .setParameter("categoryId", categoryId).getSingleResult().intValue();
        } finally { enma.close(); }
    }

    @Override
    public long countFavorites(String videoId) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            return enma.createQuery("SELECT COUNT(f) FROM Favorite f WHERE f.video.videoId = :videoId", Long.class)
                .setParameter("videoId", videoId).getSingleResult();
        } finally { enma.close(); }
    }

    @Override
    public long countShares(String videoId) {
        EntityManager enma = JpaConfig_24110165.getEntityManager();
        try {
            return enma.createQuery("SELECT COUNT(s) FROM Share s WHERE s.video.videoId = :videoId", Long.class)
                .setParameter("videoId", videoId).getSingleResult();
        } finally { enma.close(); }
    }
}
