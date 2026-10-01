package vn.iotstar.dao.impl;

import jakarta.persistence.EntityManager;
import java.util.List;
import vn.iotstar.config.JPAConfig_24162121;
import vn.iotstar.dao.ICategoryDAO_24162121;
import vn.iotstar.entity.Category_24162121;

public class CategoryDAOImpl_24162121 implements ICategoryDAO_24162121 {

    @Override
    public List<Category_24162121> findAll() {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Category c ORDER BY c.categoryId", Category_24162121.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Category_24162121 findById(int categoryId) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        try {
            return em.find(Category_24162121.class, categoryId);
        } finally {
            em.close();
        }
    }
}
