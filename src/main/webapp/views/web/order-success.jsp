<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Đặt hàng thành công</title>
</head>
<body>
<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="card shadow border-0 text-center py-4 mb-4">
                <div class="card-body">
                    <div class="display-3 text-success mb-3">
                        <i class="bi bi-check-circle-fill"></i>
                    </div>
                    <h2 class="h3 fw-bold text-success mb-2">Đặt hàng thành công!</h2>
                    <p class="text-muted mb-4">Cảm ơn bạn đã đặt hàng. Đơn hàng của bạn sẽ được xử lý và giao qua hình thức COD.</p>

                    <div class="alert alert-light border d-inline-block px-4 py-2 mb-0">
                        <span class="text-muted">Mã đơn hàng:</span> 
                        <strong class="text-primary fs-5">#${order.orderId}</strong>
                    </div>
                </div>
            </div>

            <div class="card shadow-sm border-0 mb-4">
                <div class="card-header bg-white py-3">
                    <h5 class="mb-0 text-primary"><i class="bi bi-info-circle-fill me-2"></i>Thông tin đơn hàng</h5>
                </div>
                <div class="card-body">
                    <div class="row mb-3">
                        <div class="col-sm-4 text-muted">Người nhận:</div>
                        <div class="col-sm-8 fw-semibold">${order.fullName}</div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-sm-4 text-muted">Số điện thoại:</div>
                        <div class="col-sm-8 fw-semibold">${order.phone}</div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-sm-4 text-muted">Địa chỉ nhận hàng:</div>
                        <div class="col-sm-8">${order.address}</div>
                    </div>
                    <c:if test="${not empty order.note}">
                        <div class="row mb-3">
                            <div class="col-sm-4 text-muted">Ghi chú:</div>
                            <div class="col-sm-8">${order.note}</div>
                        </div>
                    </c:if>
                    <div class="row mb-3">
                        <div class="col-sm-4 text-muted">Phương thức thanh toán:</div>
                        <div class="col-sm-8">
                            <span class="badge bg-success-subtle text-success border border-success-subtle">
                                <i class="bi bi-cash-stack"></i> Thanh toán khi nhận hàng (COD)
                            </span>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-sm-4 text-muted">Trạng thái đơn hàng:</div>
                        <div class="col-sm-8">
                            <span class="badge bg-warning text-dark">${order.statusText}</span>
                        </div>
                    </div>
                    <div class="row mb-0">
                        <div class="col-sm-4 text-muted">Thời gian đặt:</div>
                        <div class="col-sm-8">
                            <fmt:formatDate value="${order.createdAt}" pattern="dd/MM/yyyy HH:mm:ss" />
                        </div>
                    </div>
                </div>
            </div>

            <div class="card shadow-sm border-0 mb-4">
                <div class="card-header bg-white py-3">
                    <h5 class="mb-0 text-primary"><i class="bi bi-box-seam me-2"></i>Danh sách sản phẩm</h5>
                </div>
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-hover mb-0">
                            <thead class="table-light">
                                <tr>
                                    <th>Sản phẩm</th>
                                    <th class="text-end">Đơn giá</th>
                                    <th class="text-center">Số lượng</th>
                                    <th class="text-end">Thành tiền</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${order.details}" var="d">
                                    <tr>
                                        <td>
                                            <div class="fw-semibold">${d.videoName}</div>
                                            <small class="text-muted">Mã: ${d.videoId}</small>
                                        </td>
                                        <td class="text-end">
                                            <fmt:formatNumber value="${d.price}" pattern="#,##0" />đ
                                        </td>
                                        <td class="text-center">x${d.quantity}</td>
                                        <td class="text-end fw-semibold text-danger">
                                            <fmt:formatNumber value="${d.totalPrice}" pattern="#,##0" />đ
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                            <tfoot class="table-light">
                                <tr>
                                    <td colspan="3" class="text-end fw-bold">Tổng thanh toán (COD):</td>
                                    <td class="text-end fw-bold text-danger fs-5">
                                        <fmt:formatNumber value="${order.totalPrice}" pattern="#,##0" />đ
                                    </td>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>

            <div class="text-center d-flex justify-content-center gap-2">
                <a href="${pageContext.request.contextPath}/orders" class="btn btn-primary px-4 py-2">
                    <i class="bi bi-clock-history me-1"></i> Xem lịch sử đơn hàng
                </a>
                <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-secondary px-4 py-2">
                    <i class="bi bi-house-door-fill me-1"></i> Trở về Trang chủ
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
