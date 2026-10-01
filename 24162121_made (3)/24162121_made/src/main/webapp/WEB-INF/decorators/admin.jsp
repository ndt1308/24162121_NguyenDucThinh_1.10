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
  <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
      <a class="navbar-brand" href="${ctx}/admin/home">Trang quản trị</a>
      <ul class="navbar-nav me-auto">
        <li class="nav-item"><a class="nav-link" href="${ctx}/home">Trang Chủ</a></li>
        <li class="nav-item"><a class="nav-link" href="${ctx}/products">Sản phẩm</a></li>
        <li class="nav-item"><a class="nav-link" href="${ctx}/admin/videos">Quản lý Video</a></li>
        <li class="nav-item"><a class="nav-link" href="${ctx}/admin/home">Trang quản trị</a></li>
      </ul>
      <ul class="navbar-nav">
        <li class="nav-item"><span class="nav-link">Admin: ${sessionScope.account.fullname}</span></li>
        <li class="nav-item"><a class="nav-link" href="${ctx}/logout">Đăng xuất</a></li>
      </ul>
    </div>
  </nav>

  <!-- BODY -->
  <main class="container my-4 flex-grow-1">
    <sitemesh:write property="body"/>
  </main>

  <!-- FOOTER -->
  <footer class="bg-primary text-white text-center py-3">
    Họ tên: Nguyễn Đức Thịnh | MSSV: 24162121 | Mã đề: 03
  </footer>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
