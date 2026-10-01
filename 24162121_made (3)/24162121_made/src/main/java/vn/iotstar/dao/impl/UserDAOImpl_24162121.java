package vn.iotstar.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import vn.iotstar.config.JPAConfig_24162121;
import vn.iotstar.dao.IUserDAO_24162121;
import vn.iotstar.entity.Users_24162121;

public class UserDAOImpl_24162121 implements IUserDAO_24162121 {

    @Override
    public Users_24162121 findByUsername(String username) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        try {
            return em.find(Users_24162121.class, username);
        } finally {
            em.close();
        }
    }

    @Override
    public Users_24162121 findByEmail(String email) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        try {
            TypedQuery<Users_24162121> q = em.createQuery(
                    "SELECT u FROM Users u WHERE u.email = :email", Users_24162121.class);
            q.setParameter("email", email);
            return q.getResultList().stream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }

    @Override
    public void insert(Users_24162121 user) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(user);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Users_24162121 user) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(user);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
