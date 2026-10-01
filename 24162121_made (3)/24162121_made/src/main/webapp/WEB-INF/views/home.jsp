<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head><title>Trang chủ</title></head>
<body>
  <c:if test="${empty blocks}">
    <div class="alert alert-info">Chưa có category nào.</div>
  </c:if>

  <%-- Câu 4: mỗi Category 1 khối, hiển thị 3 video / trang --%>
  <c:forEach var="b" items="${blocks}">
    <c:set var="cid" value="${b.category.categoryId}"/>
    <c:set var="pr" value="${b.videos}"/>

    <section class="mb-5" id="cat-${cid}">
      <%-- Câu 5: Category Name (số lượng video) --%>
      <h3 class="border-bottom pb-2 mb-3">
        <c:out value="${b.category.categoryname}"/> <span class="text-muted">(${b.videoCount})</span>
      </h3>

      <c:if test="${empty pr.items}">
        <p class="text-muted">Category này chưa có video.</p>
      </c:if>

      <div class="row">
        <c:forEach var="v" items="${pr.items}">
          <div class="col-md-4 mb-3">
            <div class="card h-100 shadow-sm">
              <a href="${ctx}/video?id=${v.videoId}">
                <c:choose>
                  <c:when test="${empty v.poster}"><div class="bg-secondary text-white text-center" style="height:200px;line-height:200px">No image</div></c:when>
                  <c:when test="${fn:startsWith(v.poster, 'http')}"><img src="${v.poster}" alt="poster" class="card-img-top" style="height:200px;object-fit:cover"></c:when>
                  <c:otherwise><img src="${ctx}/uploads/${v.poster}" alt="poster" class="card-img-top" style="height:200px;object-fit:cover"></c:otherwise>
                </c:choose>
              </a>
              <div class="card-body">
                <h5 class="card-title">
                  <b>Tiêu đề:</b>
                  <a href="${ctx}/video?id=${v.videoId}" class="text-decoration-none"><c:out value="${v.title}"/></a>
                </h5>
                <p class="mb-1"><b>Mã video:</b> ${v.videoId}</p>
                <p class="mb-1"><b>Category name:</b> <c:out value="${v.category.categoryname}"/></p>
                <p class="mb-2"><b>View:</b> ${v.views}</p>
                <span class="badge bg-primary">Share(${b.shareCounts[v.videoId]})</span>
                <span class="badge bg-danger">Like(${b.likeCounts[v.videoId]})</span>
                <p class="mt-2 mb-2">
                  <b>Giá:</b> <span class="text-danger fw-bold"><fmt:formatNumber value="${v.price}" pattern="#,##0"/> đ</span>
                  <span class="text-muted small">| Còn ${v.stock}</span>
                </p>
                <c:choose>
                  <c:when test="${v.stock > 0}">
                    <form action="${ctx}/cart/add" method="post" class="d-flex gap-2">
                      <input type="hidden" name="videoId" value="${v.videoId}">
                      <input type="hidden" name="redirect" value="<c:out value='/home?${pageContext.request.queryString}'/>">
                      <input type="number" name="quantity" value="1" min="1" max="${v.stock}" class="form-control form-control-sm" style="width:75px">
                      <button type="submit" class="btn btn-sm btn-success">Thêm vào giỏ</button>
                    </form>
                  </c:when>
                  <c:otherwise><span class="badge bg-secondary">Hết hàng</span></c:otherwise>
                </c:choose>
              </div>
            </div>
          </div>
        </c:forEach>
      </div>

      <%-- Phân trang:  << 1 2 3 4 5 >>  (mỗi category có trang riêng) --%>
      <c:if test="${pr.totalPages > 1}">
        <nav>
          <ul class="pagination justify-content-center mb-0">
            <li class="page-item ${pr.page <= 1 ? 'disabled' : ''}">
              <a class="page-link" href="${ctx}/home?${b.otherParams}page_${cid}=${pr.page - 1}#cat-${cid}">&laquo;</a>
            </li>
            <c:forEach begin="${pr.beginPage}" end="${pr.endPage}" var="i">
              <li class="page-item ${i == pr.page ? 'active' : ''}">
                <a class="page-link" href="${ctx}/home?${b.otherParams}page_${cid}=${i}#cat-${cid}">${i}</a>
              </li>
            </c:forEach>
            <li class="page-item ${pr.page >= pr.totalPages ? 'disabled' : ''}">
              <a class="page-link" href="${ctx}/home?${b.otherParams}page_${cid}=${pr.page + 1}#cat-${cid}">&raquo;</a>
            </li>
          </ul>
        </nav>
      </c:if>
    </section>
  </c:forEach>
</body>
</html>
