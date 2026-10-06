<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="vi"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>Đăng ký - VideoHub</title><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style>body{background:#f1f5f9}.auth-card{max-width:520px;margin:5vh auto;border:0;border-radius:14px}.brand{color:#2563eb;font-weight:800}.field-error{font-size:.875rem;color:#dc3545;margin-top:4px}</style></head><body>
<div class="container"><div class="card auth-card shadow-sm"><div class="card-body p-4 p-md-5"><h2 class="brand text-center mb-1">VideoHub</h2><p class="text-secondary text-center mb-4">Tạo tài khoản mới</p>
<c:if test="${not empty alert}"><div class="alert alert-danger">${alert}</div></c:if><form action="${pageContext.request.contextPath}/register" method="post">
<div class="mb-3"><label class="form-label">Họ và tên</label><input class="form-control" name="fullname" value="${fullname}" maxlength="100"><c:if test="${not empty errors.fullname}"><div class="field-error">${errors.fullname}</div></c:if></div>
<div class="mb-3"><label class="form-label">Tên đăng nhập</label><input class="form-control" name="username" value="${username}" required><c:if test="${not empty errors.username}"><div class="field-error">${errors.username}</div></c:if></div>
<div class="mb-3"><label class="form-label">Email nhận OTP</label><input class="form-control" type="email" name="email" value="${email}" required><c:if test="${not empty errors.email}"><div class="field-error">${errors.email}</div></c:if></div>
<div class="mb-4"><label class="form-label">Mật khẩu</label><input class="form-control" type="password" name="password" minlength="6" required><c:if test="${not empty errors.password}"><div class="field-error">${errors.password}</div></c:if></div>
<button class="btn btn-primary w-100 py-2">Đăng ký và nhận OTP</button></form><p class="text-center mt-4 mb-0">Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập</a></p>
</div></div></div></body></html>
