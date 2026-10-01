<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head><title>Thanh toán</title></head>
<body>
  <h3 class="mb-3">Thanh toán đơn hàng</h3>

  <c:if test="${not empty error}">
    <div class="alert alert-danger"><c:out value="${error}"/></div>
  </c:if>

  <div class="row">
    <div class="col-md-7 mb-3">
      <div class="card shadow-sm">
        <div class="card-body">
          <h5 class="mb-3">Thông tin giao hàng</h5>
          <form action="${ctx}/checkout" method="post">
            <div class="mb-3">
              <label class="form-label">Người nhận *</label>
              <input type="text" name="receiver" class="form-control" maxlength="100" required
                     value="<c:out value='${receiver}'/>">
            </div>
            <div class="mb-3">
              <label class="form-label">Số điện thoại *</label>
              <input type="text" name="phone" class="form-control" maxlength="11" required
                     value="<c:out value='${phone}'/>">
            </div>
            <div class="mb-3">
              <label class="form-label">Địa chỉ giao hàng *</label>
              <input type="text" name="address" class="form-control" maxlength="300" required
                     value="<c:out value='${address}'/>">
            </div>
            <div class="mb-3">
              <label class="form-label">Ghi chú</label>
              <textarea name="note" rows="2" maxlength="500" class="form-control"><c:out value="${note}"/></textarea>
            </div>

            <h5 class="mb-2">Phương thức thanh toán</h5>
            <div class="form-check mb-3">
              <input class="form-check-input" type="radio" id="cod" checked disabled>
              <label class="form-check-label" for="cod">COD - Thanh toán khi nhận hàng</label>
            </div>

            <button type="submit" class="btn btn-success">Đặt hàng</button>
            <a href="${ctx}/cart" class="btn btn-secondary">Quay lại giỏ hàng</a>
          </form>
        </div>
      </div>
    </div>

    <div class="col-md-5">
      <div class="card shadow-sm">
        <div class="card-body">
          <h5 class="mb-3">Đơn hàng của bạn</h5>
          <ul class="list-group list-group-flush mb-3">
            <c:forEach var="i" items="${items}">
              <li class="list-group-item d-flex justify-content-between">
                <span><c:out value="${i.video.title}"/> <span class="text-muted">x ${i.quantity}</span></span>
                <span><fmt:formatNumber value="${i.lineTotal}" pattern="#,##0"/> đ</span>
              </li>
            </c:forEach>
          </ul>
          <div class="d-flex justify-content-between fw-bold fs-5">
            <span>Tổng cộng</span>
            <span class="text-danger"><fmt:formatNumber value="${total}" pattern="#,##0"/> đ</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
