package vn.iotstar.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import vn.iotstar.config.JPAConfig_24162121;
import vn.iotstar.dao.ICartDAO_24162121;
import vn.iotstar.entity.CartItems_24162121;
import vn.iotstar.entity.Users_24162121;
import vn.iotstar.entity.Videos_24162121;

public class CartDAOImpl_24162121 implements ICartDAO_24162121 {

    @Override
    public List<CartItems_24162121> findByUser(String username) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT c FROM CartItems c JOIN FETCH c.video WHERE c.user.username = :u ORDER BY c.cartItemId",
                    CartItems_24162121.class)
                    .setParameter("u", username).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public CartItems_24162121 find(String username, String videoId) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        try {
            return findOne(em, username, videoId);
        } finally {
            em.close();
        }
    }

    private CartItems_24162121 findOne(EntityManager em, String username, String videoId) {
        return em.createQuery(
                "SELECT c FROM CartItems c JOIN FETCH c.video WHERE c.user.username = :u AND c.video.videoId = :v",
                CartItems_24162121.class)
                .setParameter("u", username).setParameter("v", videoId)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public void addOrUpdate(String username, String videoId, int quantity) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            CartItems_24162121 item = findOne(em, username, videoId);
            if (item == null) {
                item = new CartItems_24162121();
                item.setUser(em.getReference(Users_24162121.class, username));
                item.setVideo(em.getReference(Videos_24162121.class, videoId));
                item.setQuantity(quantity);
                em.persist(item);
            } else {
                item.setQuantity(quantity);   // entity đang managed -> tự UPDATE khi commit
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void remove(String username, String videoId) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery("DELETE FROM CartItems c WHERE c.user.username = :u AND c.video.videoId = :v")
                    .setParameter("u", username).setParameter("v", videoId).executeUpdate();
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void clear(String username) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery("DELETE FROM CartItems c WHERE c.user.username = :u")
                    .setParameter("u", username).executeUpdate();
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public long countLines(String username) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(c) FROM CartItems c WHERE c.user.username = :u", Long.class)
                    .setParameter("u", username).getSingleResult();
        } finally {
            em.close();
        }
    }
}
