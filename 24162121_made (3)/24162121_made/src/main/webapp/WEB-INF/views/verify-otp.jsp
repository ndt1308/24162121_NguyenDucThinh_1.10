<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head><title>Xác thực OTP</title></head>
<body>
  <div class="row justify-content-center">
    <div class="col-md-5">
      <div class="card shadow-sm">
        <div class="card-body p-4">
          <h3 class="text-center mb-3">Kích hoạt tài khoản</h3>

          <c:if test="${not empty message}">
            <div class="alert alert-info"><c:out value="${message}"/></div>
          </c:if>
          <c:if test="${not empty error}">
            <div class="alert alert-danger"><c:out value="${error}"/></div>
          </c:if>

          <form action="${ctx}/verify-otp" method="post">
            <div class="mb-3">
              <label class="form-label">Nhập mã OTP (6 số) đã gửi qua email</label>
              <input type="text" name="otp" class="form-control text-center fs-4" maxlength="6" autofocus required>
            </div>
            <button type="submit" class="btn btn-primary w-100">Xác nhận</button>
          </form>
          <p class="text-center mt-3 mb-0"><a href="${ctx}/verify-otp?resend=1">Gửi lại OTP</a></p>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
