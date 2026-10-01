package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import vn.iotstar.entity.OrderStatus_24162121;
import vn.iotstar.entity.Users_24162121;
import vn.iotstar.service.IOrderService_24162121;
import vn.iotstar.service.impl.OrderServiceImpl_24162121;
import vn.iotstar.util.BusinessException_24162121;

/** Lịch sử đặt hàng, lọc theo trạng thái:  /orders?status=NEW|CONFIRMED|...  (bỏ status = tất cả). */
@WebServlet(urlPatterns = {"/orders", "/orders/cancel"})
public class OrderController_24162121 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IOrderService_24162121 orderService = new OrderServiceImpl_24162121();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!req.getServletPath().equals("/orders")) {
            resp.sendRedirect(req.getContextPath() + "/orders");
            return;
        }
        String username = ((Users_24162121) req.getSession().getAttribute("account")).getUsername();
        OrderStatus_24162121 current = OrderStatus_24162121.fromCode(req.getParameter("status"));
        Map<OrderStatus_24162121, Long> counts = orderService.countByStatus(username);
        long totalCount = counts.values().stream().mapToLong(Long::longValue).sum();

        req.setAttribute("orders", orderService.findByUser(username, current));
        req.setAttribute("statuses", OrderStatus_24162121.values());
        req.setAttribute("counts", counts);
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("current", current);
        req.getRequestDispatcher("/WEB-INF/views/orders.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = ((Users_24162121) session.getAttribute("account")).getUsername();

        long orderId;
        try {
            orderId = Long.parseLong(req.getParameter("orderId"));
        } catch (NumberFormatException e) {
            orderId = -1;
        }
        try {
            orderService.cancel(orderId, username);
            session.setAttribute("flashSuccess", "Đã hủy đơn hàng #" + orderId);
        } catch (BusinessException_24162121 e) {
            session.setAttribute("flashError", e.getMessage());
        }

        OrderStatus_24162121 back = OrderStatus_24162121.fromCode(req.getParameter("status"));
        resp.sendRedirect(req.getContextPath() + "/orders" + (back != null ? "?status=" + back.getCode() : ""));
    }
}
