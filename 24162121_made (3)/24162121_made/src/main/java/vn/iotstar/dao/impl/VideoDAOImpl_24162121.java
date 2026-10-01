package vn.iotstar.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vn.iotstar.config.JPAConfig_24162121;
import vn.iotstar.dao.IVideoDAO_24162121;
import vn.iotstar.entity.Videos_24162121;

public class VideoDAOImpl_24162121 implements IVideoDAO_24162121 {

    @Override
    public List<Videos_24162121> findPage(int page, int size) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        try {
            TypedQuery<Videos_24162121> q = em.createQuery(
                    "SELECT v FROM Videos v LEFT JOIN FETCH v.category ORDER BY v.videoId",
                    Videos_24162121.class);
            q.setFirstResult((page - 1) * size);
            q.setMaxResults(size);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long count() {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(v) FROM Videos v", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Videos_24162121 findById(String videoId) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        try {
            TypedQuery<Videos_24162121> q = em.createQuery(
                    "SELECT v FROM Videos v LEFT JOIN FETCH v.category WHERE v.videoId = :id",
                    Videos_24162121.class);
            q.setParameter("id", videoId);
            return q.getResultList().stream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }

    @Override
    public void insert(Videos_24162121 video) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(video);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Videos_24162121 video) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(video);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(String videoId) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // Xóa dữ liệu con (Favorites, Shares) trước để không vướng khóa ngoại
            em.createQuery("DELETE FROM Favorites f WHERE f.video.videoId = :id")
                    .setParameter("id", videoId).executeUpdate();
            em.createQuery("DELETE FROM Shares s WHERE s.video.videoId = :id")
                    .setParameter("id", videoId).executeUpdate();
            em.createQuery("DELETE FROM CartItems c WHERE c.video.videoId = :id")
                    .setParameter("id", videoId).executeUpdate();
            Videos_24162121 v = em.find(Videos_24162121.class, videoId);
            if (v != null) {
                em.remove(v);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /** Sinh mã video tiếp theo dạng VD0001, VD0002, ... */
    @Override
    public String nextId() {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        try {
            String max = em.createQuery(
                    "SELECT MAX(v.videoId) FROM Videos v WHERE v.videoId LIKE 'VD%'", String.class)
                    .getSingleResult();
            int n = 0;
            if (max != null) {
                try {
                    n = Integer.parseInt(max.substring(2));
                } catch (NumberFormatException e) {
                    n = 0;
                }
            }
            return String.format("VD%04d", n + 1);
        } finally {
            em.close();
        }
    }

    @Override
    public long countShares(String videoId) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(s) FROM Shares s WHERE s.video.videoId = :id", Long.class)
                    .setParameter("id", videoId).getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public long countLikes(String videoId) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(f) FROM Favorites f WHERE f.video.videoId = :id", Long.class)
                    .setParameter("id", videoId).getSingleResult();
        } finally {
            em.close();
        }
    }

    // ===== Câu 4: video của 1 category, phân trang =====
    @Override
    public List<Videos_24162121> findPageByCategory(int categoryId, int page, int size) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        try {
            TypedQuery<Videos_24162121> q = em.createQuery(
                    "SELECT v FROM Videos v JOIN FETCH v.category c "
                    + "WHERE c.categoryId = :cid ORDER BY v.videoId",
                    Videos_24162121.class);
            q.setParameter("cid", categoryId);
            q.setFirstResult((page - 1) * size);
            q.setMaxResults(size);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long countByCategory(int categoryId) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT COUNT(v) FROM Videos v WHERE v.category.categoryId = :cid", Long.class)
                    .setParameter("cid", categoryId).getSingleResult();
        } finally {
            em.close();
        }
    }

    // ===== Câu 5: đếm số video theo từng category =====
    @Override
    public Map<Integer, Long> countGroupByCategory() {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        try {
            List<Object[]> rows = em.createQuery(
                    "SELECT v.category.categoryId, COUNT(v) FROM Videos v "
                    + "WHERE v.category IS NOT NULL GROUP BY v.category.categoryId", Object[].class)
                    .getResultList();
            Map<Integer, Long> result = new HashMap<>();
            for (Object[] r : rows) {
                result.put(((Number) r[0]).intValue(), ((Number) r[1]).longValue());
            }
            return result;
        } finally {
            em.close();
        }
    }
}
