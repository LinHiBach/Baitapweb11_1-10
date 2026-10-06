package vn.hcmute.controllers.admin;

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

@WebServlet(urlPatterns = {"/admin/orders", "/admin/orders/update-status"})
public class AdminOrderController_24110165 extends HttpServlet {
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

        if (user.getRoleid() != 1) {
            resp.sendRedirect(req.getContextPath() + "/waiting");
            return;
        }

        String statusParam = req.getParameter("status");
        List<Order_24110165> orders;
        String activeStatus;

        if (statusParam == null || statusParam.trim().isEmpty() || "all".equalsIgnoreCase(statusParam.trim())) {
            orders = orderService.findAll();
            activeStatus = "all";
        } else {
            try {
                int statusVal = Integer.parseInt(statusParam.trim());
                orders = orderService.findByStatus(statusVal);
                activeStatus = String.valueOf(statusVal);
            } catch (NumberFormatException e) {
                orders = orderService.findAll();
                activeStatus = "all";
            }
        }

        Map<Integer, Long> counts = orderService.countByStatusAll();
        long totalOrders = 0;
        double totalRevenue = 0;
        List<Order_24110165> allList = orderService.findAll();
        for (Order_24110165 o : allList) {
            totalOrders++;
            if (o.getStatus() == OrderStatus_24110165.DELIVERED) {
                totalRevenue += o.getTotalPrice();
            }
        }

        req.setAttribute("orders", orders);
        req.setAttribute("activeStatus", activeStatus);
        req.setAttribute("statusCounts", counts);
        req.setAttribute("totalOrders", totalOrders);
        req.setAttribute("totalRevenue", totalRevenue);
        req.setAttribute("statusMap", OrderStatus_24110165.getAllStatusMap());
        req.setAttribute("allStatuses", OrderStatus_24110165.ALL_STATUSES);

        req.getRequestDispatcher("/views/admin/list-order.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        User_24110165 user = CookieUtils_24110165.checkAndRestoreSession(req, userService);
        if (user == null || user.getRoleid() != 1) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");
        if ("updateStatus".equalsIgnoreCase(action)) {
            try {
                int orderId = Integer.parseInt(req.getParameter("orderId"));
                int newStatus = Integer.parseInt(req.getParameter("newStatus"));
                orderService.updateStatus(orderId, newStatus);
                String returnStatus = req.getParameter("returnStatus");
                if (returnStatus == null || returnStatus.trim().isEmpty()) {
                    returnStatus = String.valueOf(newStatus);
                }
                resp.sendRedirect(req.getContextPath() + "/admin/orders?status=" + returnStatus + "&msg=update_success");
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        resp.sendRedirect(req.getContextPath() + "/admin/orders");
    }
}
