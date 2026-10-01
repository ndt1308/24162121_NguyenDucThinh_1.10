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

@WebServlet("/verify-otp")
public class VerifyOtpController_24162121 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IUserService_24162121 userService = new UserServiceImpl_24162121();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String username = session == null ? null : (String) session.getAttribute("otpUser");
        if (username == null) {
            resp.sendRedirect(req.getContextPath() + "/register");
            return;
        }

        // Gửi lại OTP: /verify-otp?resend=1
        if ("1".equals(req.getParameter("resend"))) {
            Users_24162121 user = userService.findByUsername(username);
            try {
                OtpUtil_24162121.issue(session, user);
                req.setAttribute("message", "Đã gửi lại mã OTP tới " + user.getEmail());
            } catch (MessagingException e) {
                e.printStackTrace();
                req.setAttribute("error", "Không gửi được email, kiểm tra lại cấu hình MailUtil");
            }
        } else if (session.getAttribute("otpInfo") != null) {
            req.setAttribute("message", session.getAttribute("otpInfo"));
            session.removeAttribute("otpInfo");
        }
        req.getRequestDispatcher("/WEB-INF/views/verify-otp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String username = session == null ? null : (String) session.getAttribute("otpUser");
        if (username == null) {
            resp.sendRedirect(req.getContextPath() + "/register");
            return;
        }

        String input = req.getParameter("otp") == null ? "" : req.getParameter("otp").trim();
        String saved = (String) session.getAttribute("otp");
        Long expire = (Long) session.getAttribute("otpExpire");

        if (expire == null || System.currentTimeMillis() > expire) {
            req.setAttribute("error", "Mã OTP đã hết hạn, hãy bấm Gửi lại OTP");
        } else if (!input.equals(saved)) {
            req.setAttribute("error", "Mã OTP không đúng");
        } else {
            // OTP đúng -> kích hoạt tài khoản
            Users_24162121 user = userService.findByUsername(username);
            user.setActive(true);
            userService.update(user);
            OtpUtil_24162121.clear(session);
            resp.sendRedirect(req.getContextPath() + "/login?activated=1");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/verify-otp.jsp").forward(req, resp);
    }
}
