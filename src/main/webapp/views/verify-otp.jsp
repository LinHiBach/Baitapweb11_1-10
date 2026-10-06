<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="vi"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>Xác thực OTP - VideoHub</title><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style>body{background:#f1f5f9}.auth-card{max-width:460px;margin:10vh auto;border:0;border-radius:14px}.brand{color:#2563eb;font-weight:800}.otp{font-size:26px;letter-spacing:12px;text-align:center;font-weight:700}</style></head><body>
<div class="container"><div class="card auth-card shadow-sm"><div class="card-body p-4 p-md-5"><h2 class="brand text-center">Xác thực OTP</h2><p class="text-secondary text-center">Mã gồm 6 chữ số đã gửi tới<br><strong>${email}</strong></p>
<c:if test="${not empty alert}"><div class="alert alert-danger">${alert}</div></c:if><c:if test="${not empty successAlert}"><div class="alert alert-success">${successAlert}</div></c:if>
<form action="${pageContext.request.contextPath}/verify-otp" method="post"><input type="hidden" name="email" value="${email}"><input class="form-control otp mb-3" name="otp" maxlength="6" inputmode="numeric" pattern="[0-9]{6}" required autofocus>
<button class="btn btn-primary w-100 py-2">Xác nhận</button><button class="btn btn-link w-100 mt-2" name="action" value="resend" formnovalidate>Gửi lại mã OTP</button></form>
</div></div></div></body></html>
