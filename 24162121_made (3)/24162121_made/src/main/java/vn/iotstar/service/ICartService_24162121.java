package vn.iotstar.service;

import java.util.List;
import vn.iotstar.entity.CartItems_24162121;

public interface ICartService_24162121 {
    List<CartItems_24162121> getItems(String username);
    long getTotal(List<CartItems_24162121> items);
    void add(String username, String videoId, int quantity);
    void updateQuantity(String username, String videoId, int quantity);
    void remove(String username, String videoId);
    void clear(String username);
    long countLines(String username);
}
