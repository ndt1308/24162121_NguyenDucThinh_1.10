package vn.iotstar.config;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import vn.iotstar.entity.Category_24162121;

@WebListener
public class InitListener_24162121 implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        EntityManager em = null;
        try {
            // ép Hibernate tạo/cập nhật bảng ngay khi khởi động
            em = JPAConfig_24162121.getEntityManager();
            seedCategories(em);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    /** Nếu bảng Category đang trống thì thêm sẵn 4 category mẫu để form thêm video có gì để chọn. */
    private void seedCategories(EntityManager em) {
        Long count = em.createQuery("SELECT COUNT(c) FROM Category c", Long.class).getSingleResult();
        if (count != null && count > 0) return;

        String[][] data = {
            {"Âm nhạc",  "MUSIC", "https://picsum.photos/seed/cat1/300/200"},
            {"Phim ảnh", "MOVIE", "https://picsum.photos/seed/cat2/300/200"},
            {"Thể thao", "SPORT", "https://picsum.photos/seed/cat3/300/200"},
            {"Giáo dục", "EDU",   "https://picsum.photos/seed/cat4/300/200"}
        };
        em.getTransaction().begin();
        try {
            for (String[] d : data) {
                Category_24162121 c = new Category_24162121();
                c.setCategoryname(d[0]);
                c.setCategorycode(d[1]);
                c.setImages(d[2]);
                c.setStatus(true);
                em.persist(c);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        }
    }
}
