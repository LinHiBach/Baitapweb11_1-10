package vn.hcmute.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.hcmute.entity.User_24110165;
import vn.hcmute.services.IUserService_24110165;
import vn.hcmute.services.impl.UserServiceImpl_24110165;
import vn.hcmute.utils.EmailUtils_24110165;

@WebServlet(urlPatterns = "/verify-otp")
public class VerifyOtpController_24110165 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IUserService_24110165 userService = new UserServiceImpl_24110165();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        String email = (session != null) ? (String) session.getAttribute("pendingEmail") : null;

        if (email == null || email.trim().isEmpty()) {
            email = req.getParameter("email");
        }

        if (email == null || email.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/register");
            return;
        }

        req.setAttribute("email", email);
        req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String otp = req.getParameter("otp");
        String action = req.getParameter("action");

        if (email == null || email.trim().isEmpty()) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                email = (String) session.getAttribute("pendingEmail");
            }
        }

        if (email == null || email.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/register");
            return;
        }

        // Xử lý gửi lại mã OTP (Resend)
        if ("resend".equalsIgnoreCase(action)) {
            User_24110165 user = userService.findByEmail(email.trim());
            if (user != null) {
                String newOtp = EmailUtils_24110165.generateOtp();
                user.setCode(newOtp);
                userService.update(user);
                EmailUtils_24110165.sendOtpEmail(user.getEmail(), newOtp, "register");
                HttpSession session = req.getSession(true);
                session.setAttribute("latestOtp", newOtp);
                session.setAttribute("pendingEmail", user.getEmail());
                req.setAttribute("message", "Mã OTP mới đã được gửi lại vào email của bạn!");
                req.setAttribute("successAlert", "Mã OTP mới đã được gửi lại vào email của bạn!");
            }
            req.setAttribute("email", email);
            req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
            return;
        }

        if (otp == null || otp.trim().isEmpty()) {
            req.setAttribute("error", "Vui lòng nhập mã OTP xác thực!");
            req.setAttribute("alert", "Vui lòng nhập mã OTP xác thực!");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
            return;
        }

        if (!otp.trim().matches("^[0-9]{6}$")) {
            req.setAttribute("error", "Mã OTP phải gồm đúng 6 chữ số!");
            req.setAttribute("alert", "Mã OTP phải gồm đúng 6 chữ số!");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
            return;
        }

        // Tìm User_24110165 theo Mã OTP và Email
        User_24110165 user = userService.findByCodeAndEmail(otp.trim(), email.trim());
        if (user != null) {
            // Kích hoạt tài khoản thành công
            user.setStatus(1);
            user.setCode(null); // Xóa OTP đã dùng
            userService.update(user);

            // Xóa session pending
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.removeAttribute("pendingEmail");
                session.removeAttribute("latestOtp");
            }

            resp.sendRedirect(req.getContextPath() + "/login?activated=1");
        } else {
            req.setAttribute("error", "Mã OTP không đúng hoặc đã hết hạn!");
            req.setAttribute("alert", "Mã OTP không đúng hoặc đã hết hạn!");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
        }
    }
}
