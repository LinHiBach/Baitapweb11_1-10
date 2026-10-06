<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html><html lang="vi"><head><title>Trang quản trị</title></head><body><main class="container py-4">
<h1>Trang chủ Admin</h1><p>Xin chào ${sessionScope.account.fullname}</p>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/videos">Quản trị Videos</a>
</main></body></html>
