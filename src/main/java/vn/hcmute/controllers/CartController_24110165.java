package vn.hcmute.controllers;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.hcmute.entity.CartItem_24110165;
import vn.hcmute.entity.Video_24110165;
import vn.hcmute.services.IVideoService_24110165;
import vn.hcmute.services.impl.VideoServiceImpl_24110165;

@WebServlet("/cart")
public class CartController_24110165 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IVideoService_24110165 videoService = new VideoServiceImpl_24110165();

    @SuppressWarnings("unchecked")
    private Map<String, CartItem_24110165> getCart(HttpSession session) {
        Map<String, CartItem_24110165> cart = (Map<String, CartItem_24110165>) session.getAttribute("cart");
        if (cart == null) {
            cart = new LinkedHashMap<>();
            session.setAttribute("cart", cart);
        }
        return cart;
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
        String action = req.getParameter("action");
        HttpSession session = req.getSession(true);

        if (action == null || action.trim().isEmpty() || "view".equalsIgnoreCase(action)) {
            Map<String, CartItem_24110165> cart = getCart(session);
            req.setAttribute("cart", cart);
            req.setAttribute("totalPrice", calculateTotal(cart));
            req.getRequestDispatcher("/views/web/cart.jsp").forward(req, resp);
            return;
        }

        // Với action 'add', kiểm tra đăng nhập
        if ("add".equalsIgnoreCase(action)) {
            if (session.getAttribute("account") == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            String videoId = req.getParameter("videoId");
            int qty = 1;
            try {
                String qtyStr = req.getParameter("qty");
                if (qtyStr != null && !qtyStr.trim().isEmpty()) {
                    qty = Integer.parseInt(qtyStr.trim());
                }
            } catch (Exception ignored) {
                qty = 1;
            }

            if (videoId != null && !videoId.trim().isEmpty()) {
                Video_24110165 video = videoService.findById(videoId.trim());
                if (video != null) {
                    Map<String, CartItem_24110165> cart = getCart(session);
                    if (cart.containsKey(video.getVideoId())) {
                        CartItem_24110165 existing = cart.get(video.getVideoId());
                        existing.setQuantitySafe(existing.getQuantity() + qty);
                    } else {
                        CartItem_24110165 item = new CartItem_24110165(video.getVideoId(), video.getTitle(),
                                video.getPoster(), video.getPrice(), qty);
                        cart.put(video.getVideoId(), item);
                    }
                    session.setAttribute("cart", cart);
                }
            }
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        if ("remove".equalsIgnoreCase(action)) {
            String videoId = req.getParameter("videoId");
            if (videoId != null) {
                Map<String, CartItem_24110165> cart = getCart(session);
                cart.remove(videoId.trim());
                session.setAttribute("cart", cart);
            }
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        if ("update".equalsIgnoreCase(action)) {
            String videoId = req.getParameter("videoId");
            String qtyStr = req.getParameter("qty");
            if (videoId != null && qtyStr != null) {
                Map<String, CartItem_24110165> cart = getCart(session);
                if (cart.containsKey(videoId.trim())) {
                    try {
                        int qty = Integer.parseInt(qtyStr.trim());
                        if (qty <= 0) {
                            cart.remove(videoId.trim());
                        } else {
                            cart.get(videoId.trim()).setQuantitySafe(qty);
                        }
                    } catch (Exception ignored) {
                    }
                    session.setAttribute("cart", cart);
                }
            }
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        if ("clear".equalsIgnoreCase(action)) {
            Map<String, CartItem_24110165> cart = getCart(session);
            cart.clear();
            session.setAttribute("cart", cart);
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
