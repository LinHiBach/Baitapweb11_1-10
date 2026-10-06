package vn.hcmute.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.hcmute.entity.Order_24110165;
import vn.hcmute.entity.User_24110165;
import vn.hcmute.services.IOrderService_24110165;
import vn.hcmute.services.IUserService_24110165;
import vn.hcmute.services.impl.OrderServiceImpl_24110165;
import vn.hcmute.services.impl.UserServiceImpl_24110165;
import vn.hcmute.utils.CookieUtils_24110165;
import vn.hcmute.utils.OrderStatus_24110165;

@WebServlet(urlPatterns = {"/orders", "/order-history"})
public class OrderHistoryController_24110165 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IOrderService_24110165 orderService = new OrderServiceImpl_24110165();
    private final IUserService_24110165 userService = new UserServiceImpl_24110165();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        User_24110165 user = CookieUtils_24110165.checkAndRestoreSession(req, userService);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");
        if ("cancel".equalsIgnoreCase(action)) {
            String orderIdStr = req.getParameter("orderId");
            try {
                int orderId = Integer.parseInt(orderIdStr);
                boolean success = orderService.cancelOrder(orderId, user.getUsername());
                if (success) {
                    resp.sendRedirect(req.getContextPath() + "/orders?status=" + OrderStatus_24110165.CANCELLED + "&msg=cancel_success");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/orders?status=all&msg=cancel_failed");
                }
                return;
            } catch (Exception e) {
                resp.sendRedirect(req.getContextPath() + "/orders?status=all");
                return;
            }
        }

        String statusParam = req.getParameter("status");
        List<Order_24110165> orders;
        String activeStatus;

        if (statusParam == null || statusParam.trim().isEmpty() || "all".equalsIgnoreCase(statusParam.trim())) {
            orders = orderService.findByUsername(user.getUsername());
            activeStatus = "all";
        } else {
            try {
                int statusVal = Integer.parseInt(statusParam.trim());
                orders = orderService.findByUsernameAndStatus(user.getUsername(), statusVal);
                activeStatus = String.valueOf(statusVal);
            } catch (NumberFormatException e) {
                orders = orderService.findByUsername(user.getUsername());
                activeStatus = "all";
            }
        }

        Map<Integer, Long> counts = orderService.countByStatusForUser(user.getUsername());
        long totalOrders = 0;
        for (Long c : counts.values()) {
            totalOrders += c;
        }

        req.setAttribute("orders", orders);
        req.setAttribute("activeStatus", activeStatus);
        req.setAttribute("statusCounts", counts);
        req.setAttribute("totalOrders", totalOrders);
        req.setAttribute("statusMap", OrderStatus_24110165.getAllStatusMap());
        req.setAttribute("allStatuses", OrderStatus_24110165.ALL_STATUSES);
        req.setAttribute("user", user);

        req.getRequestDispatcher("/views/web/order-history.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
