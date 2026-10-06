package vn.hcmute.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.hcmute.entity.User_24110165;
import vn.hcmute.services.IUserService_24110165;
import vn.hcmute.services.impl.UserServiceImpl_24110165;
import vn.hcmute.utils.Constant_24110165;
import vn.hcmute.utils.CookieUtils_24110165;

@WebServlet(urlPatterns = {"/admin", "/admin/home"})
public class AdminHomeController_24110165 extends HttpServlet {
    private static final long serialVersionUID = 1L;
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

        req.getRequestDispatcher(Constant_24110165.Path.ADMIN_HOME).forward(req, resp);
    }
}
