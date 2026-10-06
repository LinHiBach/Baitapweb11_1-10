package vn.hcmute.dao;

import java.util.List;
import vn.hcmute.entity.Order_24110165;

public interface IOrderDao_24110165 {
    Order_24110165 insert(Order_24110165 order);
    Order_24110165 findById(int orderId);
    List<Order_24110165> findByUsername(String username);
    List<Order_24110165> findByUsernameAndStatus(String username, int status);
    List<Order_24110165> findAll();
    List<Order_24110165> findByStatus(int status);
    void update(Order_24110165 order);
}
