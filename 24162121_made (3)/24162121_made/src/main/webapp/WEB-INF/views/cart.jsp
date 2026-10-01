<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head><title>Giỏ hàng</title></head>
<body>
  <h3 class="mb-3">Giỏ hàng của bạn</h3>

  <c:choose>
    <c:when test="${empty items}">
      <div class="alert alert-info">Giỏ hàng đang trống.</div>
      <a href="${ctx}/home" class="btn btn-primary">Tiếp tục mua sắm</a>
    </c:when>
    <c:otherwise>
      <div class="table-responsive">
        <table class="table table-bordered align-middle">
          <thead class="table-light">
            <tr>
              <th>Sản phẩm</th>
              <th class="text-end">Đơn giá</th>
              <th style="width:210px">Số lượng</th>
              <th class="text-end">Thành tiền</th>
              <th style="width:90px"></th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="i" items="${items}">
              <tr>
                <td>
                  <a href="${ctx}/video?id=${i.video.videoId}" class="text-decoration-none">
                    <c:out value="${i.video.title}"/>
                  </a>
                  <c:if test="${i.quantity > i.video.stock}">
                    <div class="text-danger small">Chỉ còn ${i.video.stock} sản phẩm - hãy giảm số lượng</div>
                  </c:if>
                </td>
                <td class="text-end"><fmt:formatNumber value="${i.video.price}" pattern="#,##0"/> đ</td>
                <td>
                  <form action="${ctx}/cart/update" method="post" class="d-flex gap-2 align-items-center">
                    <input type="hidden" name="videoId" value="${i.video.videoId}">
                    <input type="number" name="quantity" value="${i.quantity}" min="1" max="${i.video.stock}"
                           class="form-control form-control-sm" style="width:80px">
                    <button type="submit" class="btn btn-sm btn-outline-primary">Cập nhật</button>
                  </form>
                  <div class="text-muted small">Còn ${i.video.stock} sản phẩm</div>
                </td>
                <td class="text-end fw-bold"><fmt:formatNumber value="${i.lineTotal}" pattern="#,##0"/> đ</td>
                <td class="text-center">
                  <form action="${ctx}/cart/remove" method="post" onsubmit="return confirm('Xóa sản phẩm này khỏi giỏ hàng?')">
                    <input type="hidden" name="videoId" value="${i.video.videoId}">
                    <button type="submit" class="btn btn-sm btn-outline-danger">Xóa</button>
                  </form>
                </td>
              </tr>
            </c:forEach>
          </tbody>
          <tfoot>
            <tr>
              <td colspan="3" class="text-end fw-bold">Tổng cộng</td>
              <td class="text-end fw-bold text-danger fs-5"><fmt:formatNumber value="${total}" pattern="#,##0"/> đ</td>
              <td></td>
            </tr>
          </tfoot>
        </table>
      </div>

      <div class="d-flex flex-wrap gap-2">
        <a href="${ctx}/home" class="btn btn-outline-secondary">&laquo; Tiếp tục mua sắm</a>
        <form action="${ctx}/cart/clear" method="post" onsubmit="return confirm('Xóa toàn bộ giỏ hàng?')">
          <button type="submit" class="btn btn-outline-danger">Xóa tất cả</button>
        </form>
        <a href="${ctx}/checkout" class="btn btn-success ms-auto">Thanh toán &raquo;</a>
      </div>
    </c:otherwise>
  </c:choose>
</body>
</html>
