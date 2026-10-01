package vn.iotstar.service.impl;

import java.util.List;
import java.util.Map;
import vn.iotstar.dao.IOrderDAO_24162121;
import vn.iotstar.dao.impl.OrderDAOImpl_24162121;
import vn.iotstar.entity.OrderStatus_24162121;
import vn.iotstar.entity.Orders_24162121;
import vn.iotstar.service.IOrderService_24162121;
import vn.iotstar.util.BusinessException_24162121;

public class OrderServiceImpl_24162121 implements IOrderService_24162121 {
    private final IOrderDAO_24162121 dao = new OrderDAOImpl_24162121();

    @Override
    public Orders_24162121 placeOrder(String username, String receiver, String phone, String address, String note) {
        receiver = receiver == null ? "" : receiver.trim();
        phone = phone == null ? "" : phone.trim();
        address = address == null ? "" : address.trim();
        note = note == null ? "" : note.trim();

        if (receiver.isEmpty()) throw new BusinessException_24162121("Vui lòng nhập tên người nhận");
        if (receiver.length() > 100) throw new BusinessException_24162121("Tên người nhận tối đa 100 ký tự");
        if (!phone.matches("0\\d{9,10}")) throw new BusinessException_24162121("Số điện thoại không hợp lệ (10-11 số, bắt đầu bằng 0)");
        if (address.isEmpty()) throw new BusinessException_24162121("Vui lòng nhập địa chỉ giao hàng");
        if (address.length() > 300) throw new BusinessException_24162121("Địa chỉ tối đa 300 ký tự");
        if (note.length() > 500) throw new BusinessException_24162121("Ghi chú tối đa 500 ký tự");

        return dao.placeOrder(username, receiver, phone, address, note);
    }

    @Override
    public List<Orders_24162121> findByUser(String username, OrderStatus_24162121 status) {
        return dao.findByUser(username, status);
    }

    @Override
    public Map<OrderStatus_24162121, Long> countByStatus(String username) {
        return dao.countByStatus(username);
    }

    @Override
    public void cancel(long orderId, String username) {
        dao.cancel(orderId, username);
    }
}
