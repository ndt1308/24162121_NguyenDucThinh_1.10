package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import vn.iotstar.entity.Users_24162121;
import vn.iotstar.service.IUserService_24162121;
import vn.iotstar.service.impl.CartServiceImpl_24162121;
import vn.iotstar.service.impl.UserServiceImpl_24162121;

@WebServlet("/login")
public class LoginController_24162121 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IUserService_24162121 userService = new UserServiceImpl_24162121();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            req.setAttribute("error", "Vui lòng nhập tên đăng nhập và mật khẩu");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        Users_24162121 user = userService.findByUsername(username.trim());

        if (user == null || !password.equals(user.getPassword())) {
            req.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu");
            req.setAttribute("username", username);
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        if (!Boolean.TRUE.equals(user.getActive())) {
            req.setAttribute("error", "Tài khoản chưa được kích hoạt. Vui lòng đăng ký lại hoặc nhập mã OTP.");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        // Đăng nhập thành công -> lưu vào Session
        HttpSession session = req.getSession(true);
        session.setAttribute("account", user);
        session.setAttribute("cartCount", new CartServiceImpl_24162121().countLines(user.getUsername()));

        if (Boolean.TRUE.equals(user.getAdmin())) {
            resp.sendRedirect(req.getContextPath() + "/admin/home");
        } else {
            // user thường: quay lại trang đang định vào (giỏ hàng, đơn hàng...) hoặc về trang chủ
            String back = (String) session.getAttribute("returnUrl");
            session.removeAttribute("returnUrl");
            resp.sendRedirect(req.getContextPath() + (back != null ? back : "/home"));
        }
    }
}
