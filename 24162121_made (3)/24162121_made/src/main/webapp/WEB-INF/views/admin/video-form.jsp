<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head><title>${editing ? 'Sửa video' : 'Thêm video'}</title></head>
<body>
  <div class="row justify-content-center">
    <div class="col-md-8">
      <div class="card shadow-sm">
        <div class="card-body p-4">
          <h3 class="mb-3">${editing ? 'Cập nhật video' : 'Thêm video mới'}</h3>

          <c:if test="${not empty error}">
            <div class="alert alert-danger"><c:out value="${error}"/></div>
          </c:if>

          <form action="${ctx}/admin/videos/${editing ? 'edit' : 'add'}" method="post" enctype="multipart/form-data">
            <input type="hidden" name="page" value="${page}">

            <c:if test="${editing}">
              <div class="mb-3">
                <label class="form-label">Mã video</label>
                <input type="text" name="videoId" class="form-control" value="${video.videoId}" readonly>
              </div>
            </c:if>

            <div class="mb-3">
              <label class="form-label">Tiêu đề *</label>
              <input type="text" name="title" class="form-control" maxlength="200" value="<c:out value='${video.title}'/>" required>
            </div>

            <div class="mb-3">
              <label class="form-label">Category *</label>
              <select name="categoryId" class="form-select" required>
                <option value="">-- Chọn category --</option>
                <c:forEach var="c" items="${categories}">
                  <option value="${c.categoryId}" ${video.category != null && video.category.categoryId == c.categoryId ? 'selected' : ''}>
                    <c:out value="${c.categoryname}"/>
                  </option>
                </c:forEach>
              </select>
            </div>

            <div class="mb-3">
              <label class="form-label">Lượt xem</label>
              <input type="number" name="views" min="0" class="form-control" value="${video.views}">
            </div>

            <div class="row">
              <div class="col-md-6 mb-3">
                <label class="form-label">Giá (VND)</label>
                <input type="number" name="price" min="0" class="form-control" value="${video.price}">
              </div>
              <div class="col-md-6 mb-3">
                <label class="form-label">Tồn kho</label>
                <input type="number" name="stock" min="0" class="form-control" value="${video.stock}">
              </div>
            </div>

            <div class="mb-3">
              <label class="form-label">Mô tả</label>
              <textarea name="description" rows="4" maxlength="500" class="form-control"><c:out value="${video.description}"/></textarea>
            </div>

            <div class="mb-3">
              <label class="form-label">Poster (ảnh jpg, png, gif, webp)</label>
              <c:if test="${editing && not empty video.poster}">
                <div class="mb-2">
                  <c:set var="v" value="${video}"/>
                  <c:choose>
                    <c:when test="${fn:startsWith(v.poster, 'http')}"><img src="${v.poster}" alt="poster" style="height:90px"></c:when>
                    <c:otherwise><img src="${ctx}/uploads/${v.poster}" alt="poster" style="height:90px"></c:otherwise>
                  </c:choose>
                  <div class="form-text">Để trống nếu không muốn đổi poster.</div>
                </div>
              </c:if>
              <input type="file" name="poster" class="form-control" accept="image/*">
            </div>

            <div class="form-check mb-3">
              <input class="form-check-input" type="checkbox" name="active" id="active" ${video.active ? 'checked' : ''}>
              <label class="form-check-label" for="active">Hoạt động</label>
            </div>

            <button type="submit" class="btn btn-primary">${editing ? 'Cập nhật' : 'Thêm mới'}</button>
            <a href="${ctx}/admin/videos?page=${page}" class="btn btn-secondary">Hủy</a>
          </form>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
