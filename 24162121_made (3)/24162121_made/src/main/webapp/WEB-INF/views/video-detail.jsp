<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head><title><c:out value="${video.title}"/></title></head>
<body>
  <div class="card shadow-sm">
    <div class="card-body">
      <div class="row">
        <!-- [poster] -->
        <div class="col-md-5 mb-3">
          <c:choose>
            <c:when test="${empty video.poster}"><div class="bg-secondary text-white text-center py-5">No image</div></c:when>
            <c:when test="${fn:startsWith(video.poster, 'http')}"><img src="${video.poster}" alt="poster" class="img-fluid rounded"></c:when>
            <c:otherwise><img src="${ctx}/uploads/${video.poster}" alt="poster" class="img-fluid rounded"></c:otherwise>
          </c:choose>
        </div>

        <div class="col-md-7">
          <h3><b>Tiêu đề:</b> <c:out value="${video.title}"/></h3>
          <p class="mb-1"><b>Mã video:</b> ${video.videoId}</p>
          <p class="mb-1"><b>Category name:</b> <c:out value="${video.category.categoryname}"/></p>
          <p class="mb-2"><b>View:</b> ${video.views}</p>
          <p class="mb-2">
            <span class="badge bg-primary fs-6">Share(${shareCount})</span>
            <span class="badge bg-danger fs-6">Like(${likeCount})</span>
          </p>
          <p class="mb-2"><b>Giá:</b> <span class="text-danger fw-bold fs-5"><fmt:formatNumber value="${video.price}" pattern="#,##0"/> đ</span>
             <span class="text-muted">| Còn ${video.stock} sản phẩm</span></p>
          <c:choose>
            <c:when test="${video.stock > 0}">
              <form action="${ctx}/cart/add" method="post" class="d-flex gap-2 mb-3" style="max-width:260px">
                <input type="hidden" name="videoId" value="${video.videoId}">
                <input type="hidden" name="redirect" value="/video?id=${video.videoId}">
                <input type="number" name="quantity" value="1" min="1" max="${video.stock}" class="form-control">
                <button type="submit" class="btn btn-success text-nowrap">Thêm vào giỏ</button>
              </form>
            </c:when>
            <c:otherwise><span class="badge bg-secondary mb-3">Hết hàng</span></c:otherwise>
          </c:choose>
          <p><b>Mô tả:</b><br><c:out value="${video.description}"/></p>
        </div>
      </div>
      <a href="${ctx}/home" class="btn btn-outline-secondary btn-sm">&laquo; Về trang chủ</a>
    </div>
  </div>
</body>
</html>
