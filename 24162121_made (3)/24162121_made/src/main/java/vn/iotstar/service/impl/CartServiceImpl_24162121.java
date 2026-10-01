package vn.iotstar.service.impl;

import java.util.List;
import vn.iotstar.dao.ICartDAO_24162121;
import vn.iotstar.dao.IVideoDAO_24162121;
import vn.iotstar.dao.impl.CartDAOImpl_24162121;
import vn.iotstar.dao.impl.VideoDAOImpl_24162121;
import vn.iotstar.entity.CartItems_24162121;
import vn.iotstar.entity.Videos_24162121;
import vn.iotstar.service.ICartService_24162121;
import vn.iotstar.util.BusinessException_24162121;

public class CartServiceImpl_24162121 implements ICartService_24162121 {
    private final ICartDAO_24162121 cartDAO = new CartDAOImpl_24162121();
    private final IVideoDAO_24162121 videoDAO = new VideoDAOImpl_24162121();

    @Override
    public List<CartItems_24162121> getItems(String username) {
        return cartDAO.findByUser(username);
    }

    @Override
    public long getTotal(List<CartItems_24162121> items) {
        long total = 0;
        for (CartItems_24162121 i : items) total += i.getLineTotal();
        return total;
    }

    @Override
    public void add(String username, String videoId, int quantity) {
        if (quantity < 1) throw new BusinessException_24162121("Số lượng phải lớn hơn 0");
        Videos_24162121 v = videoId == null ? null : videoDAO.findById(videoId);
        if (v == null || !Boolean.TRUE.equals(v.getActive())) {
            throw new BusinessException_24162121("Sản phẩm không tồn tại hoặc đã ngừng bán");
        }
        int stock = v.getStock();
        if (stock <= 0) throw new BusinessException_24162121("Sản phẩm đã hết hàng");

        CartItems_24162121 existing = cartDAO.find(username, videoId);
        int current = existing == null ? 0 : existing.getQuantity();
        if (current + quantity > stock) {
            throw new BusinessException_24162121("Chỉ còn " + stock + " sản phẩm trong kho"
                    + (current > 0 ? " (trong giỏ bạn đã có " + current + ")" : ""));
        }
        cartDAO.addOrUpdate(username, videoId, current + quantity);
    }

    @Override
    public void updateQuantity(String username, String videoId, int quantity) {
        if (quantity < 1) throw new BusinessException_24162121("Số lượng phải lớn hơn 0 (muốn bỏ sản phẩm hãy bấm Xóa)");
        CartItems_24162121 existing = cartDAO.find(username, videoId);
        if (existing == null) throw new BusinessException_24162121("Sản phẩm không có trong giỏ hàng");
        int stock = existing.getVideo().getStock();
        if (quantity > stock) {
            throw new BusinessException_24162121("Chỉ còn " + stock + " sản phẩm trong kho");
        }
        cartDAO.addOrUpdate(username, videoId, quantity);
    }

    @Override
    public void remove(String username, String videoId) {
        cartDAO.remove(username, videoId);
    }

    @Override
    public void clear(String username) {
        cartDAO.clear(username);
    }

    @Override
    public long countLines(String username) {
        return cartDAO.countLines(username);
    }
}
