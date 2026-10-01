package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import vn.iotstar.entity.CartItems_24162121;
import vn.iotstar.entity.Users_24162121;
import vn.iotstar.service.ICartService_24162121;
import vn.iotstar.service.impl.CartServiceImpl_24162121;
import vn.iotstar.util.BusinessException_24162121;

/** Giỏ hàng: xem, thêm, sửa số lượng, xóa 1 dòng, xóa hết. */
@WebServlet(urlPatterns = {"/cart", "/cart/add", "/cart/update", "/cart/remove", "/cart/clear"})
public class CartController_24162121 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ICartService_24162121 cartService = new CartServiceImpl_24162121();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!req.getServletPath().equals("/cart")) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }
        Users_24162121 account = (Users_24162121) req.getSession().getAttribute("account");
        List<CartItems_24162121> items = cartService.getItems(account.getUsername());
        req.setAttribute("items", items);
        req.setAttribute("total", cartService.getTotal(items));
        req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = ((Users_24162121) session.getAttribute("account")).getUsername();
        String path = req.getServletPath();
        String videoId = req.getParameter("videoId");
        String redirect = "/cart";

        try {
            switch (path) {
                case "/cart/add":
                    redirect = safeRedirect(req.getParameter("redirect"));
                    cartService.add(username, videoId, parseInt(req.getParameter("quantity"), 1));
                    session.setAttribute("flashSuccess", "Đã thêm sản phẩm vào giỏ hàng");
                    break;
                case "/cart/update":
                    cartService.updateQuantity(username, videoId, parseInt(req.getParameter("quantity"), 0));
                    session.setAttribute("flashSuccess", "Đã cập nhật số lượng");
                    break;
                case "/cart/remove":
                    cartService.remove(username, videoId);
                    session.setAttribute("flashSuccess", "Đã xóa sản phẩm khỏi giỏ hàng");
                    break;
                case "/cart/clear":
                    cartService.clear(username);
                    session.setAttribute("flashSuccess", "Đã xóa toàn bộ giỏ hàng");
                    break;
                default:
                    break;
            }
        } catch (BusinessException_24162121 e) {
            session.setAttribute("flashError", e.getMessage());
        }

        session.setAttribute("cartCount", cartService.countLines(username));   // cập nhật số trên header
        resp.sendRedirect(req.getContextPath() + redirect);
    }

    /** Chỉ cho phép redirect về đường dẫn nội bộ của web. */
    private String safeRedirect(String r) {
        if (r != null && r.startsWith("/") && !r.startsWith("//")
                && !r.contains("\\") && !r.contains("\r") && !r.contains("\n")) {
            return r;
        }
        return "/cart";
    }

    private int parseInt(String s, int def) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return def;
        }
    }
}
