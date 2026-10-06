package vn.hcmute.services;

import java.util.List;
import java.util.Map;
import vn.hcmute.entity.CartItem_24110165;
import vn.hcmute.entity.Order_24110165;

public interface IOrderService_24110165 {
    Order_24110165 placeOrder(String username, String fullName, String phone, String address, String note,
            List<CartItem_24110165> cartItems);

    Order_24110165 findById(int orderId);

    List<Order_24110165> findByUsername(String username);

    List<Order_24110165> findByUsernameAndStatus(String username, int status);

    List<Order_24110165> findAll();

    List<Order_24110165> findByStatus(int status);

    void updateStatus(int orderId, int status);

    boolean cancelOrder(int orderId, String username);

    Map<Integer, Long> countByStatusForUser(String username);

    Map<Integer, Long> countByStatusAll();
}
