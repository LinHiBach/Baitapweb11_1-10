<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="card shadow-sm border-0"><div class="card-body p-4"><h1 class="h3 mb-4">Thêm Video</h1>
<c:if test="${not empty alert}"><div class="alert alert-danger">${alert}</div></c:if>
<form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/admin/videos/add" class="row g-3">
 <div class="col-md-6"><label class="form-label">Mã video *</label><input class="form-control" name="videoId" value="${video.videoId}" required></div>
 <div class="col-md-6"><label class="form-label">Tiêu đề *</label><input class="form-control" name="title" value="${video.title}" required></div>
 <div class="col-md-6"><label class="form-label">Chọn ảnh có sẵn</label><select class="form-select" name="existingPoster"><option value="">-- Chọn ảnh trong upload --</option><c:forEach items="${posterOptions}" var="poster"><option value="${poster}">${poster}</option></c:forEach></select></div>
 <div class="col-md-6"><label class="form-label">Hoặc tải poster mới</label><input class="form-control" type="file" name="posterFile" accept="image/png,image/jpeg,image/gif,image/webp"></div>
 <div class="col-md-6"><label class="form-label">Danh mục *</label><select class="form-select" name="categoryId" required><option value="">-- Chọn danh mục --</option><c:forEach items="${categories}" var="c"><option value="${c.categoryId}" ${video.category.categoryId == c.categoryId ? 'selected' : ''}>${c.categoryname}</option></c:forEach></select></div>
 <div class="col-12"><label class="form-label">Mô tả</label><textarea class="form-control" rows="4" name="description">${video.description}</textarea></div>
 <div class="col-12"><label><input type="radio" name="active" value="true" checked> Hoạt động</label> <label class="ms-3"><input type="radio" name="active" value="false"> Ẩn</label></div>
 <div class="col-12"><button class="btn btn-primary">Lưu</button> <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/videos">Hủy</a></div>
</form></div></div>
