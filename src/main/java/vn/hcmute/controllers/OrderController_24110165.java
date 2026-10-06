package vn.hcmute.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.hcmute.entity.CartItem_24110165;
import vn.hcmute.entity.Order_24110165;
import vn.hcmute.entity.User_24110165;
import vn.hcmute.services.IOrderService_24110165;
import vn.hcmute.services.impl.OrderServiceImpl_24110165;

@WebServlet("/order")
public class OrderController_24110165 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IOrderService_24110165 orderService = new OrderServiceImpl_24110165();

    @SuppressWarnings("unchecked")
    private Map<String, CartItem_24110165> getCart(HttpSession session) {
        return (Map<String, CartItem_24110165>) session.getAttribute("cart");
    }

    private double calculateTotal(Map<String, CartItem_24110165> cart) {
        double total = 0;
        if (cart != null) {
            for (CartItem_24110165 item : cart.values()) {
                total += item.getTotalPrice();
            }
        }
        return total;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User_24110165 user = (session != null) ? (User_24110165) session.getAttribute("account") : null;

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");
        if ("history".equalsIgnoreCase(action)) {
            resp.sendRedirect(req.getContextPath() + "/orders");
            return;
        }

        String successParam = req.getParameter("success");
        if ("1".equals(successParam)) {
            String orderIdStr = req.getParameter("orderId");
            try {
                int orderId = Integer.parseInt(orderIdStr);
                Order_24110165 order = orderService.findById(orderId);
                if (order != null && user.getUsername().equals(order.getUsername())) {
                    req.setAttribute("order", order);
                    req.getRequestDispatcher("/views/web/order-success.jsp").forward(req, resp);
                    return;
                }
            } catch (Exception ignored) {
            }
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        Map<String, CartItem_24110165> cart = (session != null) ? getCart(session) : null;
        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        req.setAttribute("cart", cart);
        req.setAttribute("totalPrice", calculateTotal(cart));
        req.setAttribute("user", user);
        req.getRequestDispatcher("/views/web/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        User_24110165 user = (session != null) ? (User_24110165) session.getAttribute("account") : null;

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Map<String, CartItem_24110165> cart = (session != null) ? getCart(session) : null;
        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        String fullName = req.getParameter("fullName");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String note = req.getParameter("note");

        if (fullName == null || fullName.trim().isEmpty() ||
            phone == null || phone.trim().isEmpty() ||
            address == null || address.trim().isEmpty()) {
            req.setAttribute("errorMsg", "Vui lòng nhập đầy đủ họ tên, số điện thoại và địa chỉ nhận hàng!");
            req.setAttribute("cart", cart);
            req.setAttribute("totalPrice", calculateTotal(cart));
            req.setAttribute("user", user);
            req.setAttribute("fullName", fullName);
            req.setAttribute("phone", phone);
            req.setAttribute("address", address);
            req.setAttribute("note", note);
            req.getRequestDispatcher("/views/web/checkout.jsp").forward(req, resp);
            return;
        }

        try {
            Order_24110165 order = orderService.placeOrder(
                    user.getUsername(),
                    fullName.trim(),
                    phone.trim(),
                    address.trim(),
                    note != null ? note.trim() : "",
                    new ArrayList<>(cart.values())
            );

            // Xóa giỏ hàng sau khi đặt thành công
            cart.clear();
            session.setAttribute("cart", cart);

            resp.sendRedirect(req.getContextPath() + "/order?success=1&orderId=" + order.getOrderId());
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMsg", "Đặt hàng thất bại: " + e.getMessage());
            req.setAttribute("cart", cart);
            req.setAttribute("totalPrice", calculateTotal(cart));
            req.setAttribute("user", user);
            req.getRequestDispatcher("/views/web/checkout.jsp").forward(req, resp);
        }
    }
}
