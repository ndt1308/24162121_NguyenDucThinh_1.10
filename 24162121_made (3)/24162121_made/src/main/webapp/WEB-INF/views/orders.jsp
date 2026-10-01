<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head><title>Lịch sử đặt hàng</title></head>
<body>
  <h3 class="mb-3">Lịch sử đặt hàng</h3>

  <%-- Lọc theo trạng thái --%>
  <ul class="nav nav-pills mb-3">
    <li class="nav-item">
      <a class="nav-link ${empty current ? 'active' : ''}" href="${ctx}/orders">
        Tất cả <span class="badge bg-light text-dark">${totalCount}</span>
      </a>
    </li>
    <c:forEach var="s" items="${statuses}">
      <li class="nav-item">
        <a class="nav-link ${current == s ? 'active' : ''}" href="${ctx}/orders?status=${s.code}">
          ${s.label} <span class="badge bg-light text-dark">${counts[s]}</span>
        </a>
      </li>
    </c:forEach>
  </ul>

  <c:if test="${empty orders}">
    <div class="alert alert-info">Không có đơn hàng nào<c:if test="${not empty current}"> ở trạng thái "${current.label}"</c:if>.</div>
  </c:if>

  <c:forEach var="o" items="${orders}">
    <div class="card shadow-sm mb-3">
      <div class="card-header d-flex justify-content-between align-items-center">
        <span><b>Đơn #${o.orderId}</b> <span class="text-muted">- ${o.orderDateText}</span></span>
        <span class="badge ${o.status.badge} fs-6">${o.status.label}</span>
      </div>
      <div class="card-body">
        <table class="table table-sm mb-3">
          <thead>
            <tr><th>Sản phẩm</th><th class="text-end">Đơn giá</th><th class="text-center">SL</th><th class="text-end">Thành tiền</th></tr>
          </thead>
          <tbody>
            <c:forEach var="d" items="${o.details}">
              <tr>
                <td><a href="${ctx}/video?id=${d.videoId}" class="text-decoration-none"><c:out value="${d.title}"/></a></td>
                <td class="text-end"><fmt:formatNumber value="${d.price}" pattern="#,##0"/> đ</td>
                <td class="text-center">${d.quantity}</td>
                <td class="text-end"><fmt:formatNumber value="${d.lineTotal}" pattern="#,##0"/> đ</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
        <div class="row">
          <div class="col-md-8 small">
            <div><b>Người nhận:</b> <c:out value="${o.receiverName}"/> - ${o.phone}</div>
            <div><b>Địa chỉ:</b> <c:out value="${o.address}"/></div>
            <c:if test="${not empty o.note}"><div><b>Ghi chú:</b> <c:out value="${o.note}"/></div></c:if>
            <div><b>Thanh toán:</b> ${o.paymentMethod} (thanh toán khi nhận hàng)</div>
          </div>
          <div class="col-md-4 text-md-end">
            <div class="fw-bold fs-5 text-danger"><fmt:formatNumber value="${o.totalAmount}" pattern="#,##0"/> đ</div>
            <c:if test="${o.status.code == 'NEW'}">
              <form action="${ctx}/orders/cancel" method="post" class="mt-2"
                    onsubmit="return confirm('Hủy đơn hàng #${o.orderId}?')">
                <input type="hidden" name="orderId" value="${o.orderId}">
                <input type="hidden" name="status" value="${current.code}">
                <button type="submit" class="btn btn-sm btn-outline-danger">Hủy đơn</button>
              </form>
            </c:if>
          </div>
        </div>
      </div>
    </div>
  </c:forEach>
</body>
</html>
