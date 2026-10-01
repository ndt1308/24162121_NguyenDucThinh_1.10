package vn.iotstar.dao;

import java.util.List;
import vn.iotstar.entity.CartItems_24162121;

public interface ICartDAO_24162121 {
    List<CartItems_24162121> findByUser(String username);
    CartItems_24162121 find(String username, String videoId);
    /** Đặt số lượng tuyệt đối cho 1 sản phẩm trong giỏ (chưa có thì thêm mới). */
    void addOrUpdate(String username, String videoId, int quantity);
    void remove(String username, String videoId);
    void clear(String username);
    long countLines(String username);
}
