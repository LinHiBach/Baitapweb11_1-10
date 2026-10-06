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
import vn.hcmute.utils.Constant_24110165;
import vn.hcmute.utils.CookieUtils_24110165;

@WebServlet(urlPatterns = "/register")
public class RegisterController_24110165 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IUserService_24110165 service = new UserServiceImpl_24110165();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        User_24110165 user = CookieUtils_24110165.checkAndRestoreSession(req, service);
        if (user != null) {
            resp.sendRedirect(req.getContextPath() + "/waiting");
            return;
        }

        req.getRequestDispatcher(Constant_24110165.Path.REGISTER).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String fullname = req.getParameter("fullname");

        // Preserve input values
        req.setAttribute("username", username != null ? username.trim() : "");
        req.setAttribute("email", email != null ? email.trim() : "");
        req.setAttribute("fullname", fullname != null ? fullname.trim() : "");

        java.util.Map<String, String> errors = new java.util.HashMap<>();

        // 1. Validate Username
        if (username == null || username.trim().isEmpty()) {
            errors.put("username", "Tên tài khoản không được để trống!");
        } else {
            String u = username.trim();
            if (u.length() < 4 || u.length() > 30) {
                errors.put("username", "Tên tài khoản phải từ 4 đến 30 ký tự!");
            } else if (!u.matches("^[a-zA-Z0-9_]+$")) {
                errors.put("username", "Tên tài khoản chỉ được chứa chữ cái, số và dấu gạch dưới (_)");
            } else if (service.checkExistUsername(u)) {
                errors.put("username", "Tên tài khoản đã tồn tại trên hệ thống!");
            }
        }

        // 2. Validate Email
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "Địa chỉ email không được để trống!");
        } else {
            String em = email.trim();
            if (!em.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                errors.put("email", "Địa chỉ email không đúng định dạng (ví dụ: user@example.com)!");
            } else if (service.checkExistEmail(em)) {
                errors.put("email", "Địa chỉ email này đã được đăng ký trước đó!");
            }
        }

        // 3. Validate Password
        if (password == null || password.trim().isEmpty()) {
            errors.put("password", "Mật khẩu không được để trống!");
        } else if (password.length() < 6) {
            errors.put("password", "Mật khẩu phải có độ dài tối thiểu 6 ký tự!");
        }

        // 4. Validate Fullname
        if (fullname != null && fullname.trim().length() > 100) {
            errors.put("fullname", "Họ và tên không được vượt quá 100 ký tự!");
        }

        // If there are validation errors, return to form with feedback
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("alert", "Vui lòng kiểm tra và sửa các thông tin chưa hợp lệ!");
            req.getRequestDispatcher(Constant_24110165.Path.REGISTER).forward(req, resp);
            return;
        }

        boolean isSuccess = service.register(username.trim(), password, email.trim(), fullname != null ? fullname.trim() : "", null);
        if (isSuccess) {
            User_24110165 newUser = service.findByEmail(email.trim());
            HttpSession session = req.getSession(true);
            session.setAttribute("pendingEmail", email.trim());
            if (newUser != null) {
                session.setAttribute("latestOtp", newUser.getCode());
            }
            resp.sendRedirect(req.getContextPath() + "/verify-otp");
        } else {
            req.setAttribute("alert", "Lỗi hệ thống! Không thể hoàn tất đăng ký lúc này.");
            req.getRequestDispatcher(Constant_24110165.Path.REGISTER).forward(req, resp);
        }
    }
}
