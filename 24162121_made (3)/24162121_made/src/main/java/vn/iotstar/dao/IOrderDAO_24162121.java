package vn.iotstar.dao;

import java.util.List;
import java.util.Map;
import vn.iotstar.entity.OrderStatus_24162121;
import vn.iotstar.entity.Orders_24162121;

public interface IOrderDAO_24162121 {
    /** Tạo đơn từ giỏ hàng trong 1 transaction: kiểm tồn kho, trừ kho, lưu đơn, xóa giỏ. */
    Orders_24162121 placeOrder(String username, String receiver, String phone, String address, String note);
    /** status = null -> lấy tất cả. */
    List<Orders_24162121> findByUser(String username, OrderStatus_24162121 status);
    Map<OrderStatus_24162121, Long> countByStatus(String username);
    /** Chỉ hủy được đơn NEW, hoàn lại tồn kho. */
    void cancel(long orderId, String username);
}
