package vn.iotstar.service;

import java.util.List;
import java.util.Map;
import vn.iotstar.entity.OrderStatus_24162121;
import vn.iotstar.entity.Orders_24162121;

public interface IOrderService_24162121 {
    Orders_24162121 placeOrder(String username, String receiver, String phone, String address, String note);
    List<Orders_24162121> findByUser(String username, OrderStatus_24162121 status);
    Map<OrderStatus_24162121, Long> countByStatus(String username);
    void cancel(long orderId, String username);
}
