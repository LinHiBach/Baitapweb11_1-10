<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Quản lý Đơn hàng - Admin</title>
    <style>
        .filter-badge-nav {
            overflow-x: auto;
            white-space: nowrap;
            background: #fff;
            padding: 10px;
            border-radius: 10px;
            border: 1px solid #e2e8f0;
        }
        .filter-badge-nav .nav-link {
            color: #475569;
            font-weight: 600;
            padding: 8px 14px;
            border-radius: 8px;
            transition: all 0.2s;
        }
        .filter-badge-nav .nav-link:hover {
            background: #f1f5f9;
            color: #2563eb;
        }
        .filter-badge-nav .nav-link.active {
            background: #2563eb;
            color: #fff;
        }
    </style>
</head>
<body>
<div class="d-flex flex-wrap justify-content-between align-items-center mb-4">
    <div>
        <h1 class="h3 mb-1 fw-bold text-dark"><i class="bi bi-receipt text-primary me-2"></i>Quản lý Đơn hàng</h1>
        <span class="text-secondary">
            Tổng cộng: <strong>${totalOrders}</strong> đơn hàng | Doanh thu giao thành công: 
            <strong class="text-success"><fmt:formatNumber value="${totalRevenue}" pattern="#,##0" />đ</strong>
        </span>
    </div>
    <div>
        <a href="${pageContext.request.contextPath}/orders" target="_blank" class="btn btn-outline-primary btn-sm">
            <i class="bi bi-box-arrow-up-right me-1"></i>Xem trang Lịch sử (User)
        </a>
    </div>
</div>

<c:if test="${param.msg == 'update_success'}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <i class="bi bi-check-circle-fill me-2"></i>Đã cập nhật trạng thái đơn hàng thành công!
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<!-- Thanh Lọc Trạng Thái Tabs -->
<div class="filter-badge-nav mb-4 shadow-sm">
    <ul class="nav nav-pills flex-nowrap gap-1">
        <li class="nav-item">
            <a class="nav-link ${activeStatus == 'all' ? 'active' : ''}" 
               href="${pageContext.request.contextPath}/admin/orders?status=all">
                Tất cả <span class="badge ${activeStatus == 'all' ? 'bg-light text-dark' : 'bg-secondary'} ms-1">${totalOrders}</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activeStatus == '0' ? 'active' : ''}" 
               href="${pageContext.request.contextPath}/admin/orders?status=0">
                0: Đơn hàng mới <span class="badge ${activeStatus == '0' ? 'bg-light text-dark' : 'bg-info text-dark'} ms-1">${statusCounts[0] != null ? statusCounts[0] : 0}</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activeStatus == '1' ? 'active' : ''}" 
               href="${pageContext.request.contextPath}/admin/orders?status=1">
                1: Đã xác nhận <span class="badge ${activeStatus == '1' ? 'bg-light text-dark' : 'bg-primary'} ms-1">${statusCounts[1] != null ? statusCounts[1] : 0}</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activeStatus == '2' ? 'active' : ''}" 
               href="${pageContext.request.contextPath}/admin/orders?status=2">
                2: Chuẩn bị hàng <span class="badge ${activeStatus == '2' ? 'bg-light text-dark' : 'bg-warning text-dark'} ms-1">${statusCounts[2] != null ? statusCounts[2] : 0}</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activeStatus == '3' ? 'active' : ''}" 
               href="${pageContext.request.contextPath}/admin/orders?status=3">
                3: Vận chuyển <span class="badge ${activeStatus == '3' ? 'bg-light text-dark' : 'bg-secondary'} ms-1">${statusCounts[3] != null ? statusCounts[3] : 0}</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activeStatus == '4' ? 'active' : ''}" 
               href="${pageContext.request.contextPath}/admin/orders?status=4">
                4: Giao hàng <span class="badge ${activeStatus == '4' ? 'bg-light text-dark' : 'bg-info text-white'} ms-1">${statusCounts[4] != null ? statusCounts[4] : 0}</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activeStatus == '5' ? 'active' : ''}" 
               href="${pageContext.request.contextPath}/admin/orders?status=5">
                5: Đã giao <span class="badge ${activeStatus == '5' ? 'bg-light text-dark' : 'bg-success'} ms-1">${statusCounts[5] != null ? statusCounts[5] : 0}</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activeStatus == '6' ? 'active' : ''}" 
               href="${pageContext.request.contextPath}/admin/orders?status=6">
                6: Đơn hàng hủy <span class="badge ${activeStatus == '6' ? 'bg-light text-dark' : 'bg-danger'} ms-1">${statusCounts[6] != null ? statusCounts[6] : 0}</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activeStatus == '7' ? 'active' : ''}" 
               href="${pageContext.request.contextPath}/admin/orders?status=7">
                7: Đơn hàng hoàn <span class="badge ${activeStatus == '7' ? 'bg-light text-dark' : 'bg-dark'} ms-1">${statusCounts[7] != null ? statusCounts[7] : 0}</span>
            </a>
        </li>
    </ul>
</div>

<!-- Bảng Đơn Hàng -->
<div class="card shadow-sm border-0">
    <div class="card-body p-0">
        <div class="table-responsive">
            <table class="table table-hover table-striped align-middle mb-0">
                <thead class="table-dark">
                    <tr>
                        <th style="width: 70px;">Mã ĐH</th>
                        <th>Khách hàng</th>
                        <th>Địa chỉ giao hàng</th>
                        <th>Sản phẩm</th>
                        <th>Tổng tiền (COD)</th>
                        <th>Thời gian</th>
                        <th>Trạng thái hiện tại</th>
                        <th style="width: 220px;">Cập nhật trạng thái</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${orders}" var="o">
                        <tr>
                            <td class="fw-bold text-primary">#${o.orderId}</td>
                            <td>
                                <div class="fw-semibold">${o.fullName}</div>
                                <small class="text-muted"><i class="bi bi-person"></i> ${o.username} | <i class="bi bi-telephone"></i> ${o.phone}</small>
                            </td>
                            <td>
                                <div class="small text-secondary" style="max-width: 240px;">${o.address}</div>
                                <c:if test="${not empty o.note}">
                                    <small class="text-muted fst-italic">Note: ${o.note}</small>
                                </c:if>
                            </td>
                            <td>
                                <ul class="list-unstyled mb-0 small">
                                    <c:forEach items="${o.details}" var="d">
                                        <li>• ${d.videoName} (x${d.quantity})</li>
                                    </c:forEach>
                                </ul>
                            </td>
                            <td class="fw-bold text-danger">
                                <fmt:formatNumber value="${o.totalPrice}" pattern="#,##0" />đ
                            </td>
                            <td class="small text-muted">
                                <fmt:formatDate value="${o.createdAt}" pattern="dd/MM/yyyy HH:mm" />
                            </td>
                            <td>
                                <span class="${o.statusBadgeClass} px-2 py-1 rounded small fw-semibold">
                                    <i class="bi ${o.statusIcon} me-1"></i>${o.statusText}
                                </span>
                            </td>
                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/admin/orders" class="d-flex gap-1 align-items-center">
                                    <input type="hidden" name="action" value="updateStatus">
                                    <input type="hidden" name="orderId" value="${o.orderId}">
                                    <input type="hidden" name="returnStatus" value="${activeStatus}">
                                    <select name="newStatus" class="form-select form-select-sm" style="font-size: 0.8rem;">
                                        <option value="0" ${o.status == 0 ? 'selected' : ''}>0: Đơn hàng mới</option>
                                        <option value="1" ${o.status == 1 ? 'selected' : ''}>1: Đã xác nhận</option>
                                        <option value="2" ${o.status == 2 ? 'selected' : ''}>2: Chuẩn bị hàng</option>
                                        <option value="3" ${o.status == 3 ? 'selected' : ''}>3: Vận chuyển</option>
                                        <option value="4" ${o.status == 4 ? 'selected' : ''}>4: Giao hàng</option>
                                        <option value="5" ${o.status == 5 ? 'selected' : ''}>5: Đã giao</option>
                                        <option value="6" ${o.status == 6 ? 'selected' : ''}>6: Đơn hàng hủy</option>
                                        <option value="7" ${o.status == 7 ? 'selected' : ''}>7: Đơn hàng hoàn</option>
                                    </select>
                                    <button type="submit" class="btn btn-sm btn-primary" title="Lưu trạng thái">
                                        <i class="bi bi-check2"></i>
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty orders}">
                        <tr>
                            <td colspan="8" class="text-center py-4 text-muted">
                                <i class="bi bi-inbox fs-3 d-block mb-2"></i>
                                Không tìm thấy đơn hàng nào ở trạng thái này.
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
