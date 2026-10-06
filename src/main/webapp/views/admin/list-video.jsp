<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="d-flex justify-content-between align-items-center mb-4"><div><h1 class="h3 mb-1">Quản lý Videos</h1><span class="text-secondary">Tổng cộng ${totalVideos} video</span></div><a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/videos/add">+ Thêm video</a></div>
<c:if test="${not empty sessionScope.successMsg}"><div class="alert alert-success">${sessionScope.successMsg}</div><c:remove var="successMsg" scope="session"/></c:if>
<c:if test="${not empty sessionScope.errorMsg}"><div class="alert alert-danger">${sessionScope.errorMsg}</div><c:remove var="errorMsg" scope="session"/></c:if>
<table class="table table-striped table-bordered align-middle">
  <thead><tr><th>Mã</th><th>Poster</th><th>Tiêu đề</th><th>Danh mục</th><th>Lượt xem</th><th>Trạng thái</th><th>Thao tác</th></tr></thead>
  <tbody>
  <c:forEach items="${videos}" var="video">
    <tr>
      <td>${video.videoId}</td>
      <td><c:if test="${not empty video.poster}"><img src="${pageContext.request.contextPath}/image?fname=${video.poster}" alt="${video.title}" style="width:96px;height:60px;object-fit:cover;border-radius:8px"></c:if></td>
      <td>${video.title}</td><td>${video.category.categoryname}</td><td>${video.views}</td>
      <td><c:choose><c:when test="${video.active}">Đang hoạt động</c:when><c:otherwise>Đã ẩn</c:otherwise></c:choose></td>
      <td><div class="d-flex gap-2"><a class="btn btn-sm btn-warning" href="${pageContext.request.contextPath}/admin/videos/edit?id=${video.videoId}">Sửa</a>
          <form method="post" action="${pageContext.request.contextPath}/admin/videos/delete" onsubmit="return confirm('Xóa video này?')"><input type="hidden" name="id" value="${video.videoId}"><button class="btn btn-sm btn-danger">Xóa</button></form></div></td>
    </tr>
  </c:forEach>
  <c:if test="${empty videos}"><tr><td colspan="7" class="text-center">Chưa có video.</td></tr></c:if>
  </tbody>
</table>
<nav><ul class="pagination">
  <c:forEach begin="1" end="${totalPages}" var="p"><li class="page-item ${p == currentPage ? 'active' : ''}"><a class="page-link" href="${pageContext.request.contextPath}/admin/videos?page=${p}">${p}</a></li></c:forEach>
</ul></nav>
