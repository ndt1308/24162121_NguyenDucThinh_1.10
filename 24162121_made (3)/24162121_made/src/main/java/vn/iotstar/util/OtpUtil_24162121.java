package vn.iotstar.util;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import java.security.SecureRandom;
import vn.iotstar.entity.Users_24162121;

public class OtpUtil_24162121 {
    public static final long EXPIRE_MS = 5 * 60 * 1000L; // OTP sống 5 phút
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate() {
        return String.format("%06d", RANDOM.nextInt(1_000_000));
    }

    /** Sinh OTP, lưu vào session rồi gửi mail. OTP luôn được lưu trước khi gửi nên có thể gửi lại nếu mail lỗi. */
    public static void issue(HttpSession session, Users_24162121 user) throws MessagingException {
        String otp = generate();
        session.setAttribute("otpUser", user.getUsername());
        session.setAttribute("otp", otp);
        session.setAttribute("otpExpire", System.currentTimeMillis() + EXPIRE_MS);
        System.out.println("[OTP] " + user.getUsername() + " -> " + otp); // dùng để test khi chưa cấu hình mail

        MailUtil_24162121.send(user.getEmail(), "Mã OTP kích hoạt tài khoản",
                "<h3>Xin chào " + user.getFullname() + "</h3>"
              + "<p>Mã OTP kích hoạt tài khoản của bạn là: <b style='font-size:22px'>" + otp + "</b></p>"
              + "<p>Mã có hiệu lực trong 5 phút.</p>");
    }

    public static void clear(HttpSession session) {
        session.removeAttribute("otpUser");
        session.removeAttribute("otp");
        session.removeAttribute("otpExpire");
    }
}
