<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="vi"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>Đăng nhập - VideoHub</title><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style>body{background:#f1f5f9}.auth-card{max-width:440px;margin:8vh auto;border:0;border-radius:14px}.brand{color:#2563eb;font-weight:800}</style></head><body>
<div class="container"><div class="card auth-card shadow-sm"><div class="card-body p-4 p-md-5"><h2 class="brand text-center mb-1">VideoHub</h2><p class="text-secondary text-center mb-4">Đăng nhập vào hệ thống</p>
<c:if test="${not empty alert}"><div class="alert alert-danger">${alert}</div></c:if><c:if test="${not empty successAlert}"><div class="alert alert-success">${successAlert}</div></c:if>
<form action="${pageContext.request.contextPath}/login" method="post"><div class="mb-3"><label class="form-label">Tên đăng nhập</label><input class="form-control" name="username" value="${username}" required autofocus></div>
<div class="mb-3"><label class="form-label">Mật khẩu</label><input class="form-control" type="password" name="password" required></div>
<div class="form-check mb-4"><input class="form-check-input" type="checkbox" name="remember" value="true" id="remember" ${isRemember ? 'checked' : ''}><label class="form-check-label" for="remember">Ghi nhớ đăng nhập</label></div>
<button class="btn btn-primary w-100 py-2">Đăng nhập</button></form><p class="text-center mt-4 mb-0">Chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký</a></p>
</div></div></div></body></html>
