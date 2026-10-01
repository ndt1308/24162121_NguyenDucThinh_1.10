<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head><title>Đăng ký</title></head>
<body>
  <div class="row justify-content-center">
    <div class="col-md-6">
      <div class="card shadow-sm">
        <div class="card-body p-4">
          <h3 class="text-center mb-3">Đăng ký tài khoản</h3>

          <c:if test="${not empty error}">
            <div class="alert alert-danger"><c:out value="${error}"/></div>
          </c:if>

          <form action="${ctx}/register" method="post">
            <div class="mb-3">
              <label class="form-label">Tên đăng nhập *</label>
              <input type="text" name="username" class="form-control" maxlength="50" value="<c:out value='${username}'/>" required>
            </div>
            <div class="mb-3">
              <label class="form-label">Mật khẩu *</label>
              <input type="password" name="password" class="form-control" maxlength="50" required>
            </div>
            <div class="mb-3">
              <label class="form-label">Họ và tên *</label>
              <input type="text" name="fullname" class="form-control" maxlength="50" value="<c:out value='${fullname}'/>" required>
            </div>
            <div class="mb-3">
              <label class="form-label">Email (nhận mã OTP) *</label>
              <input type="email" name="email" class="form-control" maxlength="150" value="<c:out value='${email}'/>" required>
            </div>
            <div class="mb-3">
              <label class="form-label">Số điện thoại</label>
              <input type="text" name="phone" class="form-control" maxlength="15" value="<c:out value='${phone}'/>">
            </div>
            <button type="submit" class="btn btn-success w-100">Đăng ký</button>
          </form>
          <p class="text-center mt-3 mb-0">Đã có tài khoản? <a href="${ctx}/login">Đăng nhập</a></p>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
