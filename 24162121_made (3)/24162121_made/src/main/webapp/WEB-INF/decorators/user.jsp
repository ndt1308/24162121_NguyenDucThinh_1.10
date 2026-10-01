<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title><sitemesh:write property="title"/></title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <sitemesh:write property="head"/>
</head>
<body class="d-flex flex-column min-vh-100">

  <!-- HEADER -->
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
      <a class="navbar-brand" href="${ctx}/home">Video Shop</a>
      <ul class="navbar-nav me-auto">
        <li class="nav-item"><a class="nav-link" href="${ctx}/home">Trang Chủ</a></li>
        <li class="nav-item"><a class="nav-link" href="${ctx}/products">Sản phẩm</a></li>
        <c:if test="${sessionScope.account != null && sessionScope.account.admin}">
          <li class="nav-item"><a class="nav-link" href="${ctx}/admin/home">Trang quản trị</a></li>
        </c:if>
      </ul>
      <ul class="navbar-nav">
        <c:choose>
          <c:when test="${sessionScope.account == null}">
            <li class="nav-item"><a class="nav-link" href="${ctx}/login">Đăng nhập</a></li>
          </c:when>
          <c:otherwise>
            <li class="nav-item">
              <a class="nav-link" href="${ctx}/cart">Giỏ hàng
                <span class="badge bg-warning text-dark">${empty sessionScope.cartCount ? 0 : sessionScope.cartCount}</span>
              </a>
            </li>
            <li class="nav-item"><a class="nav-link" href="${ctx}/orders">Đơn hàng</a></li>
            <li class="nav-item"><span class="nav-link">Xin chào, ${sessionScope.account.fullname}</span></li>
            <li class="nav-item"><a class="nav-link" href="${ctx}/logout">Đăng xuất</a></li>
          </c:otherwise>
        </c:choose>
      </ul>
    </div>
  </nav>

  <!-- BODY -->
  <main class="container my-4 flex-grow-1">
    <c:if test="${not empty sessionScope.flashSuccess}">
      <div class="alert alert-success alert-dismissible fade show">
        <c:out value="${sessionScope.flashSuccess}"/>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
      </div>
      <c:remove var="flashSuccess" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.flashError}">
      <div class="alert alert-danger alert-dismissible fade show">
        <c:out value="${sessionScope.flashError}"/>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
      </div>
      <c:remove var="flashError" scope="session"/>
    </c:if>
    <sitemesh:write property="body"/>
  </main>

  <!-- FOOTER -->
  <footer class="bg-dark text-white text-center py-3">
    Họ tên: Nguyễn Đức Thịnh | MSSV: 24162121 | Mã đề: 03
  </footer>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
