package vn.iotstar.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/** Bắt buộc đăng nhập khi vào giỏ hàng, thanh toán, đơn hàng. */
@WebFilter(urlPatterns = {"/cart", "/cart/*", "/checkout", "/orders", "/orders/*"})
public class UserAuthFilter_24162121 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            chain.doFilter(request, response);
            return;
        }

        session = req.getSession(true);
        if ("GET".equalsIgnoreCase(req.getMethod())) {          // nhớ trang đang vào để đăng nhập xong quay lại
            String url = req.getRequestURI().substring(req.getContextPath().length());
            if (req.getQueryString() != null) url += "?" + req.getQueryString();
            session.setAttribute("returnUrl", url);
        }
        session.setAttribute("flashError", "Vui lòng đăng nhập để tiếp tục");
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
