<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Giỏ hàng của bạn</title>
    <style>
        .cart-poster {
            width: 80px;
            height: 60px;
            object-fit: cover;
            border-radius: 6px;
        }
        .qty-input {
            width: 70px;
            text-align: center;
        }
        .table > :not(caption) > * > * {
            vertical-align: middle;
        }
    </style>
</head>
<body>
<div class="container py-4">
    <nav aria-label="breadcrumb" class="mb-4">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home">Trang chủ</a></li>
            <li class="breadcrumb-item active" aria-current="page">Giỏ hàng</li>
        </ol>
    </nav>

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="h3 mb-0"><i class="bi bi-cart3 text-primary"></i> Giỏ hàng của bạn</h1>
        <c:if test="${not empty cart and not empty cart.values()}">
            <a href="${pageContext.request.contextPath}/cart?action=clear" 
               class="btn btn-outline-danger btn-sm"
               onclick="return confirm('Bạn có chắc muốn xóa tất cả sản phẩm trong giỏ?')">
                <i class="bi bi-trash"></i> Xóa tất cả
            </a>
        </c:if>
    </div>

    <c:choose>
        <c:when test="${empty cart or empty cart.values()}">
            <div class="card shadow-sm border-0 py-5 text-center">
                <div class="card-body">
                    <div class="display-1 text-muted mb-3"><i class="bi bi-cart-x"></i></div>
                    <h3 class="h4 text-secondary">Giỏ hàng của bạn đang trống!</h3>
                    <p class="text-muted">Hãy chọn các video yêu thích để thêm vào giỏ hàng nhé.</p>
                    <a href="${pageContext.request.contextPath}/home" class="btn btn-primary mt-2">
                        <i class="bi bi-arrow-left"></i> Khám phá ngay
                    </a>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="row g-4">
                <div class="col-lg-8">
                    <div class="card shadow-sm border-0">
                        <div class="table-responsive">
                            <table class="table table-hover mb-0">
                                <thead class="table-light">
                                    <tr>
                                        <th scope="col" style="min-width: 250px;">Sản phẩm</th>
                                        <th scope="col" class="text-end">Đơn giá</th>
                                        <th scope="col" class="text-center" style="min-width: 150px;">Số lượng</th>
                                        <th scope="col" class="text-end">Thành tiền</th>
                                        <th scope="col" class="text-center" style="width: 60px;">Xóa</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${cart.values()}" var="item">
                                        <tr>
                                            <td>
                                                <div class="d-flex align-items-center gap-3">
                                                    <img src="${pageContext.request.contextPath}/image?fname=${item.poster}" 
                                                         alt="${item.title}" 
                                                         class="cart-poster shadow-sm">
                                                    <div>
                                                        <h6 class="mb-1 text-truncate" style="max-width: 200px;" title="${item.title}">
                                                            ${item.title}
                                                        </h6>
                                                        <small class="text-muted">Mã: ${item.videoId}</small>
                                                    </div>
                                                </div>
                                            </td>
                                            <td class="text-end text-nowrap">
                                                <span class="fw-semibold text-primary">
                                                    <fmt:formatNumber value="${item.price}" pattern="#,##0" />đ
                                                </span>
                                            </td>
                                            <td class="text-center">
                                                <form action="${pageContext.request.contextPath}/cart" method="get" class="d-inline-flex align-items-center justify-content-center gap-1">
                                                    <input type="hidden" name="action" value="update" />
                                                    <input type="hidden" name="videoId" value="${item.videoId}" />
                                                    
                                                    <button type="submit" name="qty" value="${item.quantity - 1}" 
                                                            class="btn btn-sm btn-outline-secondary px-2"
                                                            ${item.quantity <= 1 ? 'disabled' : ''}>
                                                        <i class="bi bi-dash"></i>
                                                    </button>
                                                    
                                                    <span class="form-control form-control-sm qty-input bg-light">${item.quantity}</span>
                                                    
                                                    <button type="submit" name="qty" value="${item.quantity + 1}" 
                                                            class="btn btn-sm btn-outline-secondary px-2"
                                                            ${item.quantity >= item.maxQuantity ? 'disabled' : ''}>
                                                        <i class="bi bi-plus"></i>
                                                    </button>
                                                </form>
                                                <div class="small text-muted mt-1" style="font-size: 11px;">Tối đa: ${item.maxQuantity}</div>
                                            </td>
                                            <td class="text-end text-nowrap">
                                                <strong class="text-danger">
                                                    <fmt:formatNumber value="${item.totalPrice}" pattern="#,##0" />đ
                                                </strong>
                                            </td>
                                            <td class="text-center">
                                                <a href="${pageContext.request.contextPath}/cart?action=remove&videoId=${item.videoId}" 
                                                   class="btn btn-sm btn-outline-danger"
                                                   onclick="return confirm('Xóa sản phẩm này khỏi giỏ?')"
                                                   title="Xóa">
                                                    <i class="bi bi-x-lg"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    
                    <div class="mt-3">
                        <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-secondary">
                            <i class="bi bi-arrow-left"></i> Tiếp tục xem sản phẩm
                        </a>
                    </div>
                </div>

                <div class="col-lg-4">
                    <div class="card shadow-sm border-0">
                        <div class="card-body">
                            <h5 class="card-title mb-3">Tóm tắt đơn hàng</h5>
                            
                            <div class="d-flex justify-content-between mb-2">
                                <span class="text-muted">Tổng số món:</span>
                                <span class="fw-semibold">${cart.size()} sản phẩm</span>
                            </div>
                            <div class="d-flex justify-content-between mb-2">
                                <span class="text-muted">Tạm tính:</span>
                                <span class="fw-semibold">
                                    <fmt:formatNumber value="${totalPrice}" pattern="#,##0" />đ
                                </span>
                            </div>
                            <div class="d-flex justify-content-between mb-2">
                                <span class="text-muted">Phí giao hàng (COD):</span>
                                <span class="text-success fw-semibold">Miễn phí</span>
                            </div>
                            
                            <hr>
                            
                            <div class="d-flex justify-content-between align-items-center mb-4">
                                <span class="h6 mb-0">Tổng thanh toán:</span>
                                <span class="h4 mb-0 text-danger fw-bold">
                                    <fmt:formatNumber value="${totalPrice}" pattern="#,##0" />đ
                                </span>
                            </div>

                            <div class="alert alert-info py-2 small mb-3">
                                <i class="bi bi-truck me-1"></i> Phương thức: <strong>Thanh toán khi nhận hàng (COD)</strong>
                            </div>

                            <a href="${pageContext.request.contextPath}/order" class="btn btn-primary btn-lg w-100 fw-semibold">
                                <i class="bi bi-credit-card me-1"></i> Tiến hành đặt hàng (COD)
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
