package vn.iotstar.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import vn.iotstar.config.JPAConfig_24162121;
import vn.iotstar.dao.IOrderDAO_24162121;
import vn.iotstar.entity.CartItems_24162121;
import vn.iotstar.entity.OrderDetails_24162121;
import vn.iotstar.entity.OrderStatus_24162121;
import vn.iotstar.entity.Orders_24162121;
import vn.iotstar.entity.Users_24162121;
import vn.iotstar.entity.Videos_24162121;
import vn.iotstar.util.BusinessException_24162121;

public class OrderDAOImpl_24162121 implements IOrderDAO_24162121 {

    @Override
    public Orders_24162121 placeOrder(String username, String receiver, String phone, String address, String note) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            List<CartItems_24162121> items = em.createQuery(
                    "SELECT c FROM CartItems c JOIN FETCH c.video WHERE c.user.username = :u ORDER BY c.cartItemId",
                    CartItems_24162121.class).setParameter("u", username).getResultList();
            if (items.isEmpty()) {
                throw new BusinessException_24162121("Giỏ hàng đang trống");
            }

            Orders_24162121 order = new Orders_24162121();
            order.setUser(em.getReference(Users_24162121.class, username));
            order.setOrderDate(LocalDateTime.now());
            order.setStatus(OrderStatus_24162121.NEW);
            order.setReceiverName(receiver);
            order.setPhone(phone);
            order.setAddress(address);
            order.setNote(note);
            order.setPaymentMethod("COD");

            long total = 0;
            List<OrderDetails_24162121> details = new ArrayList<>();
            for (CartItems_24162121 ci : items) {
                Videos_24162121 v = ci.getVideo();
                em.refresh(v, LockModeType.PESSIMISTIC_WRITE);   // khóa dòng + đọc tồn kho mới nhất

                if (!Boolean.TRUE.equals(v.getActive())) {
                    throw new BusinessException_24162121("\"" + v.getTitle() + "\" hiện không còn bán");
                }
                if (ci.getQuantity() > v.getStock()) {
                    throw new BusinessException_24162121("\"" + v.getTitle() + "\" chỉ còn " + v.getStock()
                            + " sản phẩm, vui lòng cập nhật lại giỏ hàng");
                }
                v.setStock(v.getStock() - ci.getQuantity());     // trừ kho

                OrderDetails_24162121 d = new OrderDetails_24162121();
                d.setOrder(order);
                d.setVideoId(v.getVideoId());
                d.setTitle(v.getTitle());
                d.setPrice(v.getPrice());
                d.setQuantity(ci.getQuantity());
                details.add(d);
                total += d.getLineTotal();
            }
            order.setDetails(details);
            order.setTotalAmount(total);
            em.persist(order);                                   // cascade lưu luôn OrderDetails

            for (CartItems_24162121 ci : items) {
                em.remove(ci);                                   // xóa giỏ
            }
            tx.commit();
            return order;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Orders_24162121> findByUser(String username, OrderStatus_24162121 status) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        try {
            String jpql = "SELECT o FROM Orders o LEFT JOIN FETCH o.details WHERE o.user.username = :u"
                    + (status != null ? " AND o.status = :s" : "")
                    + " ORDER BY o.orderId DESC";
            TypedQuery<Orders_24162121> q = em.createQuery(jpql, Orders_24162121.class);
            q.setParameter("u", username);
            if (status != null) q.setParameter("s", status);
            // JOIN FETCH collection làm lặp dòng Orders -> khử trùng, giữ nguyên thứ tự
            return new ArrayList<>(new LinkedHashSet<>(q.getResultList()));
        } finally {
            em.close();
        }
    }

    @Override
    public Map<OrderStatus_24162121, Long> countByStatus(String username) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        try {
            Map<OrderStatus_24162121, Long> result = new EnumMap<>(OrderStatus_24162121.class);
            for (OrderStatus_24162121 s : OrderStatus_24162121.values()) result.put(s, 0L);

            List<Object[]> rows = em.createQuery(
                    "SELECT o.status, COUNT(o) FROM Orders o WHERE o.user.username = :u GROUP BY o.status",
                    Object[].class).setParameter("u", username).getResultList();
            for (Object[] r : rows) {
                result.put((OrderStatus_24162121) r[0], ((Number) r[1]).longValue());
            }
            return result;
        } finally {
            em.close();
        }
    }

    @Override
    public void cancel(long orderId, String username) {
        EntityManager em = JPAConfig_24162121.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Orders_24162121 o = em.createQuery(
                    "SELECT o FROM Orders o LEFT JOIN FETCH o.details WHERE o.orderId = :id AND o.user.username = :u",
                    Orders_24162121.class)
                    .setParameter("id", orderId).setParameter("u", username)
                    .getResultList().stream().findFirst().orElse(null);
            if (o == null) {
                throw new BusinessException_24162121("Không tìm thấy đơn hàng");
            }
            if (o.getStatus() != OrderStatus_24162121.NEW) {
                throw new BusinessException_24162121("Chỉ được hủy đơn hàng ở trạng thái \"Đơn hàng mới\"");
            }
            for (OrderDetails_24162121 d : o.getDetails()) {     // hoàn lại tồn kho
                Videos_24162121 v = em.find(Videos_24162121.class, d.getVideoId(), LockModeType.PESSIMISTIC_WRITE);
                if (v != null) v.setStock(v.getStock() + d.getQuantity());
            }
            o.setStatus(OrderStatus_24162121.CANCELLED);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
