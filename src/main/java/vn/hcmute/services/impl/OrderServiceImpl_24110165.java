package vn.hcmute.services.impl;

import java.sql.Timestamp;
import java.util.List;
import vn.hcmute.dao.IOrderDao_24110165;
import vn.hcmute.dao.impl.OrderDaoImpl_24110165;
import vn.hcmute.entity.CartItem_24110165;
import vn.hcmute.entity.OrderDetail_24110165;
import vn.hcmute.entity.Order_24110165;
import vn.hcmute.services.IOrderService_24110165;

public class OrderServiceImpl_24110165 implements IOrderService_24110165 {
    private final IOrderDao_24110165 orderDao = new OrderDaoImpl_24110165();

    @Override
    public Order_24110165 placeOrder(String username, String fullName, String phone, String address, String note,
            List<CartItem_24110165> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalArgumentException("Giỏ hàng rỗng, không thể tạo đơn hàng!");
        }

        double totalPrice = 0;
        for (CartItem_24110165 item : cartItems) {
            totalPrice += item.getTotalPrice();
        }

        Order_24110165 order = new Order_24110165();
        order.setUsername(username);
        order.setFullName(fullName);
        order.setPhone(phone);
        order.setAddress(address);
        order.setNote(note);
        order.setTotalPrice(totalPrice);
        order.setStatus(0); // 0: Chờ xác nhận
        order.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        for (CartItem_24110165 item : cartItems) {
            OrderDetail_24110165 detail = new OrderDetail_24110165();
            detail.setOrder(order);
            detail.setVideoId(item.getVideoId());
            detail.setVideoName(item.getTitle());
            detail.setPrice(item.getPrice());
            detail.setQuantity(item.getQuantity());
            detail.setPoster(item.getPoster());
            order.addDetail(detail);
        }

        return orderDao.insert(order);
    }

    @Override
    public Order_24110165 findById(int orderId) {
        return orderDao.findById(orderId);
    }

    @Override
    public List<Order_24110165> findByUsername(String username) {
        return orderDao.findByUsername(username);
    }

    @Override
    public List<Order_24110165> findByUsernameAndStatus(String username, int status) {
        return orderDao.findByUsernameAndStatus(username, status);
    }

    @Override
    public List<Order_24110165> findAll() {
        return orderDao.findAll();
    }

    @Override
    public List<Order_24110165> findByStatus(int status) {
        return orderDao.findByStatus(status);
    }

    @Override
    public void updateStatus(int orderId, int status) {
        Order_24110165 order = orderDao.findById(orderId);
        if (order != null) {
            order.setStatus(status);
            orderDao.update(order);
        }
    }

    @Override
    public boolean cancelOrder(int orderId, String username) {
        Order_24110165 order = orderDao.findById(orderId);
        if (order != null && order.getUsername().equals(username)) {
            // Cho phép hủy nếu đơn hàng ở trạng thái Đơn hàng mới (0)
            if (order.getStatus() == vn.hcmute.utils.OrderStatus_24110165.NEW) {
                order.setStatus(vn.hcmute.utils.OrderStatus_24110165.CANCELLED);
                orderDao.update(order);
                return true;
            }
        }
        return false;
    }

    @Override
    public java.util.Map<Integer, Long> countByStatusForUser(String username) {
        List<Order_24110165> list = orderDao.findByUsername(username);
        java.util.Map<Integer, Long> map = new java.util.HashMap<>();
        for (Order_24110165 o : list) {
            map.put(o.getStatus(), map.getOrDefault(o.getStatus(), 0L) + 1L);
        }
        return map;
    }

    @Override
    public java.util.Map<Integer, Long> countByStatusAll() {
        List<Order_24110165> list = orderDao.findAll();
        java.util.Map<Integer, Long> map = new java.util.HashMap<>();
        for (Order_24110165 o : list) {
            map.put(o.getStatus(), map.getOrDefault(o.getStatus(), 0L) + 1L);
        }
        return map;
    }
}
