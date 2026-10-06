<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>${video.title}</title>
    <style>
        .detail-poster {
            width: 100%;
            height: 360px;
            object-fit: cover;
            border-radius: 8px;
        }
    </style>
</head>
<body>
<div class="container py-4">
    <nav aria-label="breadcrumb" class="mb-3">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home">Trang chủ</a></li>
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home">${video.category.categoryname}</a></li>
            <li class="breadcrumb-item active" aria-current="page">${video.title}</li>
        </ol>
    </nav>

    <div class="card border-0 shadow-sm">
        <div class="row g-0">
            <div class="col-md-5 p-3">
                <img class="detail-poster shadow-sm" 
                     src="${pageContext.request.contextPath}/image?fname=${video.poster}" 
                     alt="${video.title}">
            </div>
            <div class="col-md-7">
                <div class="card-body p-4 d-flex flex-column h-100 justify-content-between">
                    <div>
                        <h1 class="h3 fw-bold mb-3">${video.title}</h1>
                        <p class="text-muted mb-2"><strong>Mã sản phẩm:</strong> <span class="badge bg-secondary">${video.videoId}</span></p>
                        <p class="text-muted mb-2"><strong>Danh mục:</strong> ${video.category.categoryname}</p>
                        <p class="text-muted mb-3"><strong>Lượt xem:</strong> <i class="bi bi-eye"></i> ${video.views}</p>
                        
                        <div class="d-flex gap-4 text-muted mb-4 small">
                            <span><i class="bi bi-heart-fill text-danger"></i> ${likeCount} Yêu thích</span>
                            <span><i class="bi bi-share-fill text-primary"></i> ${shareCount} Chia sẻ</span>
                        </div>

                        <div class="p-3 bg-light rounded mb-4">
                            <span class="text-muted d-block small mb-1">Đơn giá bán:</span>
                            <span class="h2 text-danger fw-bold mb-0">
                                <fmt:formatNumber value="${video.price}" pattern="#,##0" />đ
                            </span>
                        </div>
                    </div>

                    <!-- Form thêm vào giỏ hàng -->
                    <form action="${pageContext.request.contextPath}/cart" method="get" class="d-flex align-items-center gap-3">
                        <input type="hidden" name="action" value="add" />
                        <input type="hidden" name="videoId" value="${video.videoId}" />
                        
                        <div class="d-flex align-items-center gap-2">
                            <label for="detailQty" class="form-label mb-0 fw-semibold">Số lượng:</label>
                            <input type="number" id="detailQty" name="qty" class="form-control text-center" 
                                   value="1" min="1" max="10" style="width: 80px;" required>
                        </div>

                        <button type="submit" class="btn btn-warning btn-lg fw-bold px-4">
                            <i class="bi bi-cart-plus me-1"></i> Thêm vào giỏ
                        </button>
                    </form>
                </div>
            </div>
        </div>

        <div class="card-body border-top p-4">
            <h2 class="h5 fw-bold mb-3"><i class="bi bi-card-text me-2"></i>Mô tả chi tiết</h2>
            <p class="text-secondary mb-0" style="line-height: 1.7;">
                ${not empty video.description ? video.description : 'Đang cập nhật mô tả cho sản phẩm này.'}
            </p>
        </div>
    </div>
</div>
</body>
</html>
