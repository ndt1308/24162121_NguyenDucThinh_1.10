package vn.iotstar.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import vn.iotstar.entity.Users_24162121;
import vn.iotstar.service.IUserService_24162121;
import vn.iotstar.service.impl.UserServiceImpl_24162121;
import vn.iotstar.util.OtpUtil_24162121;

@WebServlet("/register")
public class RegisterController_24162121 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IUserService_24162121 userService = new UserServiceImpl_24162121();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = trim(req.getParameter("username"));
        String password = req.getParameter("password");
        String fullname = trim(req.getParameter("fullname"));
        String email = trim(req.getParameter("email"));
        String phone = trim(req.getParameter("phone"));

        String error = null;
        if (username.isEmpty() || password == null || password.isEmpty() || fullname.isEmpty() || email.isEmpty()) {
            error = "Vui lòng nhập đầy đủ thông tin bắt buộc";
        } else if (username.length() > 50 || password.length() > 50) {
            error = "Tên đăng nhập và mật khẩu tối đa 50 ký tự";
        } else if (!email.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
            error = "Email không hợp lệ";
        } else if (userService.existsUsername(username)) {
            error = "Tên đăng nhập đã tồn tại";
        } else if (userService.existsEmail(email)) {
            error = "Email đã được sử dụng";
        }

        if (error != null) {
            req.setAttribute("error", error);
            req.setAttribute("username", username);
            req.setAttribute("fullname", fullname);
            req.setAttribute("email", email);
            req.setAttribute("phone", phone);
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        // Lưu tài khoản ở trạng thái CHƯA kích hoạt (Active = false), Admin = false
        Users_24162121 user = new Users_24162121();
        user.setUsername(username);
        user.setPassword(password);
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAdmin(false);
        user.setActive(false);
        userService.insert(user);

        // Sinh OTP + gửi mail, rồi chuyển sang trang nhập OTP
        HttpSession session = req.getSession(true);
        try {
            OtpUtil_24162121.issue(session, user);
            session.setAttribute("otpInfo", "Mã OTP đã được gửi tới " + email);
        } catch (MessagingException e) {
            e.printStackTrace();
            session.setAttribute("otpInfo", "Không gửi được email (kiểm tra cấu hình MailUtil). Bấm \"Gửi lại OTP\" để thử lại.");
        }
        resp.sendRedirect(req.getContextPath() + "/verify-otp");
    }

    private String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
