<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Thanh toán đơn hàng (COD)</title>
    <style>
        .checkout-item-poster {
            width: 50px;
            height: 40px;
            object-fit: cover;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<div class="container py-4">
    <nav aria-label="breadcrumb" class="mb-4">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home">Trang chủ</a></li>
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/cart">Giỏ hàng</a></li>
            <li class="breadcrumb-item active" aria-current="page">Thanh toán COD</li>
        </ol>
    </nav>

    <h1 class="h3 mb-4"><i class="bi bi-shield-check text-success"></i> Xác nhận đặt hàng (COD)</h1>

    <c:if test="${not empty errorMsg}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="bi bi-exclamation-triangle-fill me-2"></i> ${errorMsg}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/order" method="post">
        <div class="row g-4">
            <!-- Cột trái: Thông tin nhận hàng -->
            <div class="col-lg-7">
                <div class="card shadow-sm border-0 mb-4">
                    <div class="card-header bg-white py-3">
                        <h5 class="mb-0 text-primary"><i class="bi bi-geo-alt-fill me-2"></i>Thông tin người nhận</h5>
                    </div>
                    <div class="card-body">
                        <div class="mb-3">
                            <label for="fullName" class="form-label fw-semibold">Họ và tên người nhận <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="fullName" name="fullName" 
                                   value="${not empty fullName ? fullName : user.fullname}" required placeholder="Nguyễn Văn A">
                        </div>

                        <div class="mb-3">
                            <label for="phone" class="form-label fw-semibold">Số điện thoại liên hệ <span class="text-danger">*</span></label>
                            <input type="tel" class="form-control" id="phone" name="phone" 
                                   value="${not empty phone ? phone : user.phone}" required placeholder="0901234567">
                        </div>

                        <div class="mb-3">
                            <label for="address" class="form-label fw-semibold">Địa chỉ giao hàng <span class="text-danger">*</span></label>
                            <textarea class="form-control" id="address" name="address" rows="3" required 
                                      placeholder="Số nhà, tên đường, phường/xã, quận/huyện, tỉnh/thành phố...">${not empty address ? address : ''}</textarea>
                        </div>

                        <div class="mb-3">
                            <label for="note" class="form-label fw-semibold">Ghi chú giao hàng (nếu có)</label>
                            <textarea class="form-control" id="note" name="note" rows="2" 
                                      placeholder="Ví dụ: Giao vào giờ hành chính, gọi trước khi đến...">${not empty note ? note : ''}</textarea>
                        </div>
                    </div>
                </div>

                <!-- Phương thức thanh toán COD -->
                <div class="card shadow-sm border-0">
                    <div class="card-header bg-white py-3">
                        <h5 class="mb-0 text-primary"><i class="bi bi-wallet2 me-2"></i>Phương thức thanh toán</h5>
                    </div>
                    <div class="card-body">
                        <div class="form-check p-3 border rounded bg-light d-flex align-items-center gap-3">
                            <input class="form-check-input ms-0 mt-0" type="radio" name="paymentMethod" id="codPayment" value="COD" checked>
                            <label class="form-check-label w-100" for="codPayment">
                                <div class="d-flex justify-content-between align-items-center">
                                    <span class="fw-bold text-dark"><i class="bi bi-cash-stack text-success fs-5 me-1"></i> Thanh toán tiền mặt khi nhận hàng (COD)</span>
                                    <span class="badge bg-success">Mặc định</span>
                                </div>
                                <div class="text-muted small mt-1">
                                    Bạn chỉ cần thanh toán tiền mặt trực tiếp cho nhân viên giao hàng khi nhận được gói hàng.
                                </div>
                            </label>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Cột phải: Tóm tắt đơn hàng -->
            <div class="col-lg-5">
                <div class="card shadow-sm border-0 sticky-top" style="top: 80px;">
                    <div class="card-header bg-white py-3">
                        <h5 class="mb-0 text-primary"><i class="bi bi-bag-check-fill me-2"></i>Đơn hàng của bạn</h5>
                    </div>
                    <div class="card-body p-0">
                        <div class="list-group list-group-flush" style="max-height: 280px; overflow-y: auto;">
                            <c:forEach items="${cart.values()}" var="item">
                                <div class="list-group-item d-flex align-items-center justify-content-between py-3">
                                    <div class="d-flex align-items-center gap-2">
                                        <img src="${pageContext.request.contextPath}/image?fname=${item.poster}" 
                                             alt="${item.title}" class="checkout-item-poster shadow-sm">
                                        <div>
                                            <div class="fw-semibold text-truncate" style="max-width: 170px;" title="${item.title}">
                                                ${item.title}
                                            </div>
                                            <small class="text-muted">SL: x${item.quantity}</small>
                                        </div>
                                    </div>
                                    <div class="text-end">
                                        <span class="fw-semibold text-danger">
                                            <fmt:formatNumber value="${item.totalPrice}" pattern="#,##0" />đ
                                        </span>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <div class="p-3 bg-light border-top">
                            <div class="d-flex justify-content-between mb-2">
                                <span class="text-muted">Tạm tính:</span>
                                <span class="fw-semibold">
                                    <fmt:formatNumber value="${totalPrice}" pattern="#,##0" />đ
                                </span>
                            </div>
                            <div class="d-flex justify-content-between mb-2">
                                <span class="text-muted">Phí vận chuyển COD:</span>
                                <span class="text-success fw-semibold">0đ (Miễn phí)</span>
                            </div>
                            <hr class="my-2">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <span class="h6 mb-0">Tổng tiền cần trả:</span>
                                <span class="h4 mb-0 text-danger fw-bold">
                                    <fmt:formatNumber value="${totalPrice}" pattern="#,##0" />đ
                                </span>
                            </div>

                            <button type="submit" class="btn btn-success btn-lg w-100 fw-bold shadow-sm">
                                <i class="bi bi-check-circle-fill me-1"></i> Xác nhận đặt hàng ngay
                            </button>
                            
                            <div class="text-center mt-2">
                                <a href="${pageContext.request.contextPath}/cart" class="text-decoration-none small text-muted">
                                    <i class="bi bi-arrow-left"></i> Quay lại chỉnh sửa giỏ hàng
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>
