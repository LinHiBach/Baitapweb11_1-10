<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="card shadow-sm border-0"><div class="card-body p-4"><h1 class="h3 mb-4">Cập nhật Video</h1>
<c:if test="${not empty alert}"><div class="alert alert-danger">${alert}</div></c:if>
<form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/admin/videos/edit" class="row g-3">
 <input type="hidden" name="oldPoster" value="${video.poster}">
 <div class="col-md-6"><label class="form-label">Mã video</label><input class="form-control" name="videoId" value="${video.videoId}" readonly></div>
 <div class="col-md-6"><label class="form-label">Tiêu đề *</label><input class="form-control" name="title" value="${video.title}" required></div>
 <div class="col-md-4"><label class="form-label">Poster hiện tại</label><div><img src="${pageContext.request.contextPath}/image?fname=${video.poster}" alt="Poster" style="width:140px;height:90px;object-fit:cover;border-radius:8px"></div></div>
 <div class="col-md-4"><label class="form-label">Đổi sang ảnh có sẵn</label><select class="form-select" name="existingPoster"><option value="">-- Giữ ảnh hiện tại --</option><c:forEach items="${posterOptions}" var="poster"><option value="${poster}" ${poster == video.poster ? 'selected' : ''}>${poster}</option></c:forEach></select></div>
 <div class="col-md-4"><label class="form-label">Hoặc tải poster mới</label><input class="form-control" type="file" name="posterFile" accept="image/png,image/jpeg,image/gif,image/webp"></div>
 <div class="col-md-6"><label class="form-label">Danh mục *</label><select class="form-select" name="categoryId" required><c:forEach items="${categories}" var="c"><option value="${c.categoryId}" ${video.category.categoryId == c.categoryId ? 'selected' : ''}>${c.categoryname}</option></c:forEach></select></div>
 <div class="col-12"><label class="form-label">Mô tả</label><textarea class="form-control" rows="4" name="description">${video.description}</textarea></div>
 <div class="col-12"><label><input type="radio" name="active" value="true" ${video.active ? 'checked' : ''}> Hoạt động</label> <label class="ms-3"><input type="radio" name="active" value="false" ${not video.active ? 'checked' : ''}> Ẩn</label></div>
 <div class="col-12"><button class="btn btn-primary">Cập nhật</button> <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/videos">Hủy</a></div>
</form></div></div>
