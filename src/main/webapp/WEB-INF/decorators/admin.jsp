<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title><sitemesh:write property="title"/> - Admin</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
  <sitemesh:write property="head"/>
  <style>
    :root{--sidebar:250px;--header:64px;--nav:#0f172a;--blue:#2563eb}
    *{box-sizing:border-box}html,body{margin:0;min-height:100%;overflow-x:hidden;background:#f1f5f9}
    body{font-family:Arial,sans-serif;color:#0f172a}
    .admin-sidebar{position:fixed;inset:0 auto 0 0;width:var(--sidebar);background:var(--nav);color:#fff;z-index:20;padding:22px 14px;display:flex;flex-direction:column}
    .brand{font-size:21px;font-weight:800;padding:0 10px 22px;border-bottom:1px solid #334155}
    .admin-name{padding:18px 10px;color:#cbd5e1}.admin-name strong{display:block;color:#fff;margin-bottom:3px}
    .admin-nav{display:grid;gap:7px}.admin-nav a{color:#cbd5e1;text-decoration:none;padding:12px 14px;border-radius:9px;font-weight:600}.admin-nav a:hover,.admin-nav a.active{background:var(--blue);color:#fff}
    .admin-nav i{width:24px}.logout-side{margin-top:auto!important;color:#fca5a5!important}
    .admin-header{position:fixed;top:0;left:var(--sidebar);right:0;height:var(--header);background:#fff;border-bottom:1px solid #e2e8f0;z-index:15;display:flex;align-items:center;justify-content:space-between;padding:0 28px;box-shadow:0 2px 10px rgba(15,23,42,.06)}
    .admin-header h1{font-size:18px;margin:0;font-weight:800}.admin-header a{text-decoration:none}
    .admin-page{margin-left:var(--sidebar);padding-top:var(--header);min-height:100vh;display:flex;flex-direction:column}
    .admin-main{padding:28px;flex:1;width:100%;max-width:1600px;margin:0 auto}
    .admin-footer{background:#1f2937;color:#fff;text-align:center;padding:17px 20px;margin:0 28px 24px;border-radius:10px}.admin-footer strong{color:#60a5fa}.admin-footer .exam{color:#f59e0b}
    @media(max-width:850px){:root{--sidebar:0px}.admin-sidebar{display:none}.admin-header{left:0}.admin-main{padding:18px}.admin-footer{margin:0 18px 18px}}
  </style>
</head>
<body>
  <aside class="admin-sidebar">
    <div class="brand"><i class="bi bi-camera-video-fill me-2"></i>Admin Video</div>
    <div class="admin-name"><strong>${sessionScope.account.fullname}</strong><small>Administrator</small></div>
    <nav class="admin-nav">
      <a href="${pageContext.request.contextPath}/admin/home"><i class="bi bi-house-door-fill"></i>Trang chủ Admin</a>
      <a href="${pageContext.request.contextPath}/admin/videos"><i class="bi bi-collection-play-fill"></i>Quản trị Videos</a>
      <a href="${pageContext.request.contextPath}/admin/orders"><i class="bi bi-receipt"></i>Quản lý Đơn hàng</a>
      <a href="${pageContext.request.contextPath}/home"><i class="bi bi-globe"></i>Xem trang User</a>
    </nav>
    <a class="admin-nav logout-side" href="${pageContext.request.contextPath}/logout"><i class="bi bi-box-arrow-right me-2"></i>Đăng xuất</a>
  </aside>
  <header class="admin-header">
    <h1>Trang quản trị Video</h1>
    <a class="btn btn-outline-danger btn-sm" href="${pageContext.request.contextPath}/logout"><i class="bi bi-box-arrow-right"></i> Đăng xuất</a>
  </header>
  <div class="admin-page">
    <main class="admin-main"><sitemesh:write property="body"/></main>
    <footer class="admin-footer">Họ và tên: <strong>Lâm Huy Bách</strong> &nbsp;|&nbsp; MSSV: <strong>24110165</strong> &nbsp;|&nbsp; Mã đề: <strong class="exam">Đề số 3</strong></footer>
  </div>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
