package vn.hcmute.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.hcmute.entity.User_24110165;
import vn.hcmute.services.IUserService_24110165;

public class CookieUtils_24110165 {

    /**
     * Lấy giá trị cookie theo tên
     */
    public static String get(HttpServletRequest req, String name) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Thêm cookie mới với path="/"
     */
    public static void add(HttpServletResponse resp, String name, String value, int maxAgeInSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAgeInSeconds);
        cookie.setPath("/");
        cookie.setHttpOnly(true); // Tăng cường bảo mật
        resp.addCookie(cookie);
    }

    /**
     * Xóa cookie triệt để với path="/"
     */
    public static void delete(HttpServletResponse resp, String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        resp.addCookie(cookie);
    }

    /**
     * Tự động khôi phục Session từ Remember Me Cookie nếu Session chưa có
     */
    public static User_24110165 checkAndRestoreSession(HttpServletRequest req, IUserService_24110165 service) {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            return (User_24110165) session.getAttribute("account");
        }

        String rememberedUsername = get(req, Constant_24110165.COOKIE_REMEMBER);
        if (rememberedUsername != null && !rememberedUsername.trim().isEmpty()) {
            User_24110165 user = service.get(rememberedUsername.trim());
            if (user != null) {
                session = req.getSession(true);
                session.setAttribute("account", user);
                return user;
            }
        }
        return null;
    }
}
