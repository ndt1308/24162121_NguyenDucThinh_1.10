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
import vn.iotstar.entity.Orders_24162121;
import vn.iotstar.entity.Users_24162121;
import vn.iotstar.service.ICartService_24162121;
import vn.iotstar.service.IOrderService_24162121;
import vn.iotstar.service.impl.CartServiceImpl_24162121;
import vn.iotstar.service.impl.OrderServiceImpl_24162121;
import vn.iotstar.util.BusinessException_24162121;

/** Thanh toán đơn hàng bằng COD (thanh toán khi nhận hàng). */
@WebServlet("/checkout")
public class CheckoutController_24162121 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ICartService_24162121 cartService = new CartServiceImpl_24162121();
    private final IOrderService_24162121 orderService = new OrderServiceImpl_24162121();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Users_24162121 account = (Users_24162121) req.getSession().getAttribute("account");
        // điền sẵn thông tin từ tài khoản
        req.setAttribute("receiver", account.getFullname());
        req.setAttribute("phone", account.getPhone());
        showForm(req, resp, account);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Users_24162121 account = (Users_24162121) session.getAttribute("account");

        String receiver = req.getParameter("receiver");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String note = req.getParameter("note");

        try {
            Orders_24162121 order = orderService.placeOrder(account.getUsername(), receiver, phone, address, note);
            session.setAttribute("cartCount", 0L);
            session.setAttribute("flashSuccess", "Đặt hàng thành công! Mã đơn hàng: #" + order.getOrderId()
                    + " - thanh toán khi nhận hàng (COD)");
            resp.sendRedirect(req.getContextPath() + "/orders");
        } catch (BusinessException_24162121 e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("receiver", receiver);
            req.setAttribute("phone", phone);
            req.setAttribute("address", address);
            req.setAttribute("note", note);
            showForm(req, resp, account);
        }
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp, Users_24162121 account)
            throws ServletException, IOException {
        List<CartItems_24162121> items = cartService.getItems(account.getUsername());
        if (items.isEmpty()) {
            req.getSession().setAttribute("flashError", "Giỏ hàng đang trống");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }
        req.setAttribute("items", items);
        req.setAttribute("total", cartService.getTotal(items));
        req.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(req, resp);
    }
}
