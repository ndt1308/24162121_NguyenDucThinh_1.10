<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head><title>Quản lý Video</title></head>
<body>
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h3 class="mb-0">Quản lý Video</h3>
    <a href="${ctx}/admin/videos/add" class="btn btn-success">+ Thêm video</a>
  </div>

  <c:choose>
    <c:when test="${param.msg == 'added'}"><div class="alert alert-success">Thêm video thành công</div></c:when>
    <c:when test="${param.msg == 'updated'}"><div class="alert alert-success">Cập nhật video thành công</div></c:when>
    <c:when test="${param.msg == 'deleted'}"><div class="alert alert-success">Đã xóa video</div></c:when>
  </c:choose>

  <div class="table-responsive">
    <table class="table table-bordered table-hover align-middle">
      <thead class="table-dark">
        <tr>
          <th>#</th><th>Poster</th><th>Mã video</th><th>Tiêu đề</th>
          <th>Category</th><th>Views</th><th>Trạng thái</th><th style="width:210px">Thao tác</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="v" items="${pageResult.items}" varStatus="st">
          <tr>
            <td>${(pageResult.page - 1) * pageResult.size + st.count}</td>
            <td>
                <c:choose>
                  <c:when test="${empty v.poster}"><div class="bg-secondary text-white text-center small" style="width:90px;height:56px;line-height:56px">No image</div></c:when>
                  <c:when test="${fn:startsWith(v.poster, 'http')}"><img src="${v.poster}" alt="poster" style="width:90px;height:56px;object-fit:cover"></c:when>
                  <c:otherwise><img src="${ctx}/uploads/${v.poster}" alt="poster" style="width:90px;height:56px;object-fit:cover"></c:otherwise>
                </c:choose>
            </td>
            <td>${v.videoId}</td>
            <td><c:out value="${v.title}"/></td>
            <td><c:out value="${v.category.categoryname}"/></td>
            <td>${v.views}</td>
            <td>
              <c:choose>
                <c:when test="${v.active}"><span class="badge bg-success">Hoạt động</span></c:when>
                <c:otherwise><span class="badge bg-secondary">Ẩn</span></c:otherwise>
              </c:choose>
            </td>
            <td>
              <a href="${ctx}/video?id=${v.videoId}" class="btn btn-sm btn-info" target="_blank">Xem</a>
              <a href="${ctx}/admin/videos/edit?id=${v.videoId}&page=${pageResult.page}" class="btn btn-sm btn-warning">Sửa</a>
              <form action="${ctx}/admin/videos/delete" method="post" class="d-inline"
                    onsubmit="return confirm('Bạn có chắc muốn xóa video này?');">
                <input type="hidden" name="id" value="${v.videoId}">
                <input type="hidden" name="page" value="${pageResult.page}">
                <button type="submit" class="btn btn-sm btn-danger">Xóa</button>
              </form>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty pageResult.items}">
          <tr><td colspan="8" class="text-center text-muted">Chưa có video nào</td></tr>
        </c:if>
      </tbody>
    </table>
  </div>

  <!-- PHÂN TRANG: 6 video / trang -->
  <c:if test="${pageResult.totalPages > 1}">
    <nav>
      <ul class="pagination justify-content-center">
        <li class="page-item ${pageResult.page <= 1 ? 'disabled' : ''}">
          <a class="page-link" href="${ctx}/admin/videos?page=${pageResult.page - 1}">&laquo;</a>
        </li>
        <c:forEach begin="1" end="${pageResult.totalPages}" var="i">
          <li class="page-item ${i == pageResult.page ? 'active' : ''}">
            <a class="page-link" href="${ctx}/admin/videos?page=${i}">${i}</a>
          </li>
        </c:forEach>
        <li class="page-item ${pageResult.page >= pageResult.totalPages ? 'disabled' : ''}">
          <a class="page-link" href="${ctx}/admin/videos?page=${pageResult.page + 1}">&raquo;</a>
        </li>
      </ul>
    </nav>
  </c:if>
  <p class="text-center text-muted">Tổng: ${pageResult.totalItems} video - Trang ${pageResult.page}/${pageResult.totalPages}</p>
</body>
</html>
