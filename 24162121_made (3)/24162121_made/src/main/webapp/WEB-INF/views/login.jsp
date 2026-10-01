<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head><title>Đăng nhập</title></head>
<body>
  <div class="row justify-content-center">
    <div class="col-md-5">
      <div class="card shadow-sm">
        <div class="card-body p-4">
          <h3 class="text-center mb-3">Đăng nhập</h3>

          <c:if test="${param.activated == '1'}">
            <div class="alert alert-success">Kích hoạt tài khoản thành công, hãy đăng nhập.</div>
          </c:if>
          <c:if test="${not empty message}">
            <div class="alert alert-info"><c:out value="${message}"/></div>
          </c:if>
          <c:if test="${not empty error}">
            <div class="alert alert-danger"><c:out value="${error}"/></div>
          </c:if>

          <form action="${ctx}/login" method="post">
            <div class="mb-3">
              <label class="form-label">Tên đăng nhập</label>
              <input type="text" name="username" class="form-control" value="<c:out value='${username}'/>" required>
            </div>
            <div class="mb-3">
              <label class="form-label">Mật khẩu</label>
              <input type="password" name="password" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary w-100">Đăng nhập</button>
          </form>
          <p class="text-center mt-3 mb-0">Chưa có tài khoản? <a href="${ctx}/register">Đăng ký</a></p>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
