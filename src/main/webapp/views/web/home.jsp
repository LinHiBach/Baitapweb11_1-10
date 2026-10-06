<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Trang chủ</title>
    <style>
        .video-poster { height: 190px; object-fit: cover; }
        .video-title { font-size: 18px; font-weight: 700; min-height: 44px; }
        .category-section { margin-bottom: 42px; }
        .card { border: 0; }
    </style>
</head>
<body>
<div class="container py-4">
    <h1 class="h3 mb-4">Danh sách video & Sản phẩm</h1>
    
    <c:forEach items="${categories}" var="category">
        <section class="category-section">
            <div class="d-flex align-items-center justify-content-between mb-3">
                <h2 class="h4 mb-0">
                    ${category.categoryname} <span class="badge bg-primary">${videoCounts[category.categoryId]}</span>
                </h2>
            </div>

            <div class="row g-3">
                <c:forEach items="${videosByCategory[category.categoryId]}" var="video">
                    <div class="col-md-6 col-lg-4">
                        <article class="card h-100 shadow-sm">
                            <img class="card-img-top video-poster" 
                                 src="${pageContext.request.contextPath}/image?fname=${video.poster}" 
                                 alt="${video.title}">
                            <div class="card-body d-flex flex-column">
                                <h3 class="video-title text-truncate-2">${video.title}</h3>
                                <p class="text-secondary mb-2 small">Mã: ${video.videoId} · ${category.categoryname}</p>
                                
                                <div class="d-flex justify-content-between align-items-center mb-2">
                                    <div class="fs-5 fw-bold text-danger">
                                        <fmt:formatNumber value="${video.price}" pattern="#,##0" />đ
                                    </div>
                                    <div class="d-flex gap-2 small text-muted">
                                        <span><i class="bi bi-eye"></i> ${video.views}</span>
                                        <span><i class="bi bi-heart"></i> ${likeCounts[video.videoId]}</span>
                                        <span><i class="bi bi-share"></i> ${shareCounts[video.videoId]}</span>
                                    </div>
                                </div>

                                <div class="d-flex gap-2 mt-auto">
                                    <a class="btn btn-outline-primary btn-sm flex-fill" 
                                       href="${pageContext.request.contextPath}/video/detail?id=${video.videoId}">
                                        <i class="bi bi-info-circle"></i> Chi tiết
                                    </a>
                                    <a class="btn btn-warning btn-sm flex-fill fw-semibold" 
                                       href="${pageContext.request.contextPath}/cart?action=add&videoId=${video.videoId}">
                                       <i class="bi bi-cart-plus"></i> Thêm giỏ
                                    </a>
                                </div>
                            </div>
                        </article>
                    </div>
                </c:forEach>
            </div>

            <c:set var="current" value="${currentPages[category.categoryId]}" />
            <c:set var="pages" value="${totalPages[category.categoryId]}" />
            <nav class="mt-3">
                <ul class="pagination pagination-sm">
                    <c:if test="${current > 1}">
                        <li class="page-item">
                            <a class="page-link" href="?page_${category.categoryId}=${current-1}">&laquo;</a>
                        </li>
                    </c:if>
                    <c:forEach begin="1" end="${pages}" var="p">
                        <li class="page-item ${p == current ? 'active' : ''}">
                            <a class="page-link" href="?page_${category.categoryId}=${p}">${p}</a>
                        </li>
                    </c:forEach>
                    <c:if test="${current < pages}">
                        <li class="page-item">
                            <a class="page-link" href="?page_${category.categoryId}=${current+1}">&raquo;</a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </section>
    </c:forEach>
</div>
</body>
</html>
