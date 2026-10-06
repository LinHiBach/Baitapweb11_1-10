<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Lịch sử đặt hàng</title>
    <style>
        .order-filter-nav {
            background: #fff;
            border-radius: 14px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.04);
            overflow-x: auto;
            white-space: nowrap;
        }
        .order-filter-nav .nav-link {
            color: #64748b;
            font-weight: 600;
            padding: 14px 18px;
            border-bottom: 3px solid transparent;
            transition: all 0.2s ease;
            display: inline-flex;
            align-items: center;
            gap: 6px;
        }
        .order-filter-nav .nav-link:hover {
            color: #2563eb;
            background: rgba(37, 99, 235, 0.04);
        }
        .order-filter-nav .nav-link.active {
            color: #2563eb;
            border-bottom-color: #2563eb;
            background: rgba(37, 99, 235, 0.06);
        }
        .order-filter-nav .nav-link .badge {
            font-size: 0.72rem;
            padding: 4px 7px;
        }

        .order-card {
            background: #fff;
            border-radius: 14px;
            box-shadow: 0 4px 18px rgba(15, 23, 42, 0.05);
            border: 1px solid #e2e8f0;
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }
        .order-card:hover {
            box-shadow: 0 8px 24px rgba(15, 23, 42, 0.09);
        }
        .order-card-header {
            background: #fafafa;
            border-bottom: 1px solid #f1f5f9;
            border-top-left-radius: 14px;
            border-top-right-radius: 14px;
            padding: 14px 20px;
        }

        /* Timeline Stepper */
        .order-stepper {
            display: flex;
            align-items: center;
            justify-content: space-between;
            position: relative;
            padding: 16px 10px 10px;
            margin-bottom: 16px;
            background: #f8fafc;
            border-radius: 10px;
        }
        .order-step {
            display: flex;
            flex-direction: column;
            align-items: center;
            position: relative;
            z-index: 2;
            flex: 1;
            text-align: center;
        }
        .order-step-icon {
            width: 34px;
            height: 34px;
            border-radius: 50%;
            background: #e2e8f0;
            color: #94a3b8;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 15px;
            font-weight: bold;
            margin-bottom: 6px;
            transition: all 0.3s;
        }
        .order-step.completed .order-step-icon {
            background: #22c55e;
            color: #fff;
        }
        .order-step.current .order-step-icon {
            background: #2563eb;
            color: #fff;
            box-shadow: 0 0 0 4px rgba(37, 99, 235, 0.2);
        }
        .order-step-label {
            font-size: 0.75rem;
            font-weight: 600;
            color: #64748b;
        }
        .order-step.completed .order-step-label,
        .order-step.current .order-step-label {
            color: #0f172a;
        }
        .order-step-line {
            position: absolute;
            top: 33px;
            left: 8%;
            right: 8%;
            height: 3px;
            background: #e2e8f0;
            z-index: 1;
        }
        .order-step-line-fill {
            height: 100%;
            background: #22c55e;
            transition: width 0.3s;
        }

        .product-thumb {
            width: 65px;
            height: 65px;
            object-fit: cover;
            border-radius: 8px;
            border: 1px solid #e2e8f0;
        }
        .product-thumb-placeholder {
            width: 65px;
            height: 65px;
            border-radius: 8px;
            background: #e2e8f0;
            color: #64748b;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 22px;
        }
        .db-hint-box {
            background: linear-gradient(135deg, #eff6ff 0%, #f0fdf4 100%);
            border: 1px dashed #3b82f6;
            border-radius: 12px;
        }
    </style>
</head>
<body>
<div class="container py-4">
    <!-- Breadcrumb & Tiêu đề -->
    <div class="d-flex flex-wrap justify-content-between align-items-center mb-4">
        <div>
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb mb-1">
                    <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home" class="text-decoration-none">Trang chủ</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Lịch sử đơn hàng</li>
                </ol>
            </nav>
            <h2 class="h3 fw-bold text-dark mb-0">
                <i class="bi bi-clock-history text-primary me-2"></i>Lịch sử đặt hàng
            </h2>
        </div>
        <div class="mt-2 mt-md-0">
            <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-primary btn-sm">
                <i class="bi bi-cart-plus me-1"></i>Tiếp tục mua hàng
            </a>
            <c:if test="${not empty sessionScope.account and sessionScope.account.admin}">
                <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-primary btn-sm ms-2">
                    <i class="bi bi-gear-fill me-1"></i>Quản trị đơn hàng (Admin)
                </a>
            </c:if>
        </div>
    </div>

    <!-- Thông báo nếu có -->
    <c:if test="${param.msg == 'cancel_success'}">
        <div class="alert alert-success alert-dismissible fade show shadow-sm" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i>Bạn đã hủy đơn hàng thành công! Trạng thái đã chuyển sang <strong>Đơn hàng hủy</strong>.
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${param.msg == 'cancel_failed'}">
        <div class="alert alert-danger alert-dismissible fade show shadow-sm" role="alert">
            <i class="bi bi-exclamation-triangle-fill me-2"></i>Chỉ có thể hủy đơn hàng khi đơn còn ở trạng thái <strong>Đơn hàng mới</strong>!
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <!-- Hộp ghi chú hướng dẫn quan sát trạng thái qua Database -->
    <div class="db-hint-box p-3 mb-4">
        <div class="d-flex align-items-start">
            <i class="bi bi-database-fill-gear text-primary fs-3 me-3 mt-1"></i>
            <div class="flex-grow-1">
                <h6 class="fw-bold text-primary mb-1">
                    <i class="bi bi-info-circle me-1"></i> Hướng dẫn kiểm thử thay đổi trạng thái trong CSDL (SQL Server):
                </h6>
                <p class="mb-2 text-secondary small">
                    Vào SQL Server Management Studio (SSMS), mở CSDL <code>WebVideoDB</code> và chạy lệnh:
                </p>
                <div class="bg-dark text-light p-2 rounded font-monospace small mb-2 user-select-all">
                    UPDATE dbo.Orders SET Status = &lt;MÃ_TRẠNG_THÁI&gt; WHERE OrderId = &lt;MÃ_ĐƠN_HÀNG&gt;;
                </div>
                <div class="d-flex flex-wrap gap-2 text-muted small">
                    <span class="badge bg-info text-dark">0: Đơn hàng mới</span>
                    <span class="badge bg-primary">1: Đã xác nhận</span>
                    <span class="badge bg-warning text-dark">2: Chuẩn bị hàng</span>
                    <span class="badge bg-secondary">3: Vận chuyển</span>
                    <span class="badge bg-info text-white">4: Giao hàng</span>
                    <span class="badge bg-success">5: Đã giao</span>
                    <span class="badge bg-danger">6: Đơn hàng hủy</span>
                    <span class="badge bg-dark">7: Đơn hàng hoàn</span>
                </div>
            </div>
        </div>
    </div>

    <!-- Thanh Lọc Theo Trạng Thái (Tab Navigation 8 trạng thái + Tất cả) -->
    <div class="order-filter-nav mb-4">
        <ul class="nav nav-pills flex-nowrap p-2">
            <!-- Tab Tất cả -->
            <li class="nav-item">
                <a class="nav-link ${activeStatus == 'all' ? 'active' : ''}" 
                   href="${pageContext.request.contextPath}/orders?status=all">
                    <i class="bi bi-grid-fill"></i> Tất cả
                    <span class="badge rounded-pill ${activeStatus == 'all' ? 'bg-primary' : 'bg-secondary'}">${totalOrders}</span>
                </a>
            </li>

            <!-- 0: Đơn hàng mới -->
            <li class="nav-item">
                <a class="nav-link ${activeStatus == '0' ? 'active' : ''}" 
                   href="${pageContext.request.contextPath}/orders?status=0">
                    <i class="bi bi-clock-history text-info"></i> Đơn hàng mới
                    <span class="badge rounded-pill ${activeStatus == '0' ? 'bg-info text-dark' : 'bg-light text-dark border'}">${statusCounts[0] != null ? statusCounts[0] : 0}</span>
                </a>
            </li>

            <!-- 1: Đã xác nhận -->
            <li class="nav-item">
                <a class="nav-link ${activeStatus == '1' ? 'active' : ''}" 
                   href="${pageContext.request.contextPath}/orders?status=1">
                    <i class="bi bi-check2-circle text-primary"></i> Đã xác nhận
                    <span class="badge rounded-pill ${activeStatus == '1' ? 'bg-primary' : 'bg-light text-dark border'}">${statusCounts[1] != null ? statusCounts[1] : 0}</span>
                </a>
            </li>

            <!-- 2: Chuẩn bị hàng -->
            <li class="nav-item">
                <a class="nav-link ${activeStatus == '2' ? 'active' : ''}" 
                   href="${pageContext.request.contextPath}/orders?status=2">
                    <i class="bi bi-box-seam text-warning"></i> Chuẩn bị hàng
                    <span class="badge rounded-pill ${activeStatus == '2' ? 'bg-warning text-dark' : 'bg-light text-dark border'}">${statusCounts[2] != null ? statusCounts[2] : 0}</span>
                </a>
            </li>

            <!-- 3: Vận chuyển -->
            <li class="nav-item">
                <a class="nav-link ${activeStatus == '3' ? 'active' : ''}" 
                   href="${pageContext.request.contextPath}/orders?status=3">
                    <i class="bi bi-truck text-secondary"></i> Vận chuyển
                    <span class="badge rounded-pill ${activeStatus == '3' ? 'bg-secondary' : 'bg-light text-dark border'}">${statusCounts[3] != null ? statusCounts[3] : 0}</span>
                </a>
            </li>

            <!-- 4: Giao hàng -->
            <li class="nav-item">
                <a class="nav-link ${activeStatus == '4' ? 'active' : ''}" 
                   href="${pageContext.request.contextPath}/orders?status=4">
                    <i class="bi bi-bicycle text-primary"></i> Giao hàng
                    <span class="badge rounded-pill ${activeStatus == '4' ? 'bg-primary' : 'bg-light text-dark border'}">${statusCounts[4] != null ? statusCounts[4] : 0}</span>
                </a>
            </li>

            <!-- 5: Đã giao -->
            <li class="nav-item">
                <a class="nav-link ${activeStatus == '5' ? 'active' : ''}" 
                   href="${pageContext.request.contextPath}/orders?status=5">
                    <i class="bi bi-check-all text-success"></i> Đã giao
                    <span class="badge rounded-pill ${activeStatus == '5' ? 'bg-success' : 'bg-light text-dark border'}">${statusCounts[5] != null ? statusCounts[5] : 0}</span>
                </a>
            </li>

            <!-- 6: Đơn hàng hủy -->
            <li class="nav-item">
                <a class="nav-link ${activeStatus == '6' ? 'active' : ''}" 
                   href="${pageContext.request.contextPath}/orders?status=6">
                    <i class="bi bi-x-circle text-danger"></i> Đơn hàng hủy
                    <span class="badge rounded-pill ${activeStatus == '6' ? 'bg-danger' : 'bg-light text-dark border'}">${statusCounts[6] != null ? statusCounts[6] : 0}</span>
                </a>
            </li>

            <!-- 7: Đơn hàng hoàn -->
            <li class="nav-item">
                <a class="nav-link ${activeStatus == '7' ? 'active' : ''}" 
                   href="${pageContext.request.contextPath}/orders?status=7">
                    <i class="bi bi-arrow-counterclockwise text-dark"></i> Đơn hàng hoàn
                    <span class="badge rounded-pill ${activeStatus == '7' ? 'bg-dark' : 'bg-light text-dark border'}">${statusCounts[7] != null ? statusCounts[7] : 0}</span>
                </a>
            </li>
        </ul>
    </div>

    <!-- Danh sách Đơn Hàng -->
    <c:choose>
        <c:when test="${empty orders}">
            <!-- Empty State khi không có đơn hàng -->
            <div class="card shadow-sm border-0 py-5 text-center my-4">
                <div class="card-body">
                    <div class="display-3 text-muted mb-3">
                        <i class="bi bi-inbox"></i>
                    </div>
                    <h4 class="fw-bold text-dark">Chưa có đơn hàng nào</h4>
                    <p class="text-muted mb-4">
                        <c:choose>
                            <c:when test="${activeStatus == 'all'}">
                                Bạn chưa đặt đơn hàng nào. Hãy khám phá và chọn sản phẩm yêu thích ngay nhé!
                            </c:when>
                            <c:otherwise>
                                Hiện không có đơn hàng nào ở trạng thái "<strong>${statusMap[activeStatus]}</strong>".
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <a href="${pageContext.request.contextPath}/home" class="btn btn-primary px-4 py-2">
                        <i class="bi bi-bag-plus me-1"></i> Khám phá sản phẩm
                    </a>
                    <c:if test="${activeStatus != 'all'}">
                        <a href="${pageContext.request.contextPath}/orders?status=all" class="btn btn-outline-secondary px-4 py-2 ms-2">
                            Xem tất cả đơn
                        </a>
                    </c:if>
                </div>
            </div>
        </c:when>

        <c:otherwise>
            <!-- Lặp qua từng đơn hàng -->
            <c:forEach items="${orders}" var="o">
                <div class="order-card mb-4" id="order-${o.orderId}">
                    <!-- Card Header -->
                    <div class="order-card-header d-flex flex-wrap justify-content-between align-items-center gap-2">
                        <div class="d-flex align-items-center gap-3">
                            <span class="fs-5 fw-bold text-primary">#ĐH-${o.orderId}</span>
                            <span class="text-muted small">
                                <i class="bi bi-calendar3 me-1"></i>
                                <fmt:formatDate value="${o.createdAt}" pattern="dd/MM/yyyy HH:mm:ss" />
                            </span>
                        </div>
                        <div class="d-flex align-items-center gap-2">
                            <span class="${o.statusBadgeClass} px-3 py-2 rounded-pill fs-6 fw-semibold shadow-sm">
                                <i class="bi ${o.statusIcon} me-1"></i> ${o.statusText}
                            </span>
                        </div>
                    </div>

                    <!-- Card Body -->
                    <div class="card-body p-4">
                        <!-- Stepper tiến trình (Chỉ hiển thị cho trạng thái 0 đến 5) -->
                        <c:if test="${o.status >= 0 and o.status <= 5}">
                            <div class="order-stepper mb-4">
                                <div class="order-step-line">
                                    <div class="order-step-line-fill" style="width: ${o.status * 20}%;"></div>
                                </div>
                                <div class="order-step ${o.status > 0 ? 'completed' : (o.status == 0 ? 'current' : '')}">
                                    <div class="order-step-icon"><i class="bi ${o.status > 0 ? 'bi-check-lg' : 'bi-clock'}"></i></div>
                                    <div class="order-step-label">Đơn mới</div>
                                </div>
                                <div class="order-step ${o.status > 1 ? 'completed' : (o.status == 1 ? 'current' : '')}">
                                    <div class="order-step-icon"><i class="bi ${o.status > 1 ? 'bi-check-lg' : 'bi-check2-circle'}"></i></div>
                                    <div class="order-step-label">Đã xác nhận</div>
                                </div>
                                <div class="order-step ${o.status > 2 ? 'completed' : (o.status == 2 ? 'current' : '')}">
                                    <div class="order-step-icon"><i class="bi ${o.status > 2 ? 'bi-check-lg' : 'bi-box-seam'}"></i></div>
                                    <div class="order-step-label">Chuẩn bị hàng</div>
                                </div>
                                <div class="order-step ${o.status > 3 ? 'completed' : (o.status == 3 ? 'current' : '')}">
                                    <div class="order-step-icon"><i class="bi ${o.status > 3 ? 'bi-check-lg' : 'bi-truck'}"></i></div>
                                    <div class="order-step-label">Vận chuyển</div>
                                </div>
                                <div class="order-step ${o.status > 4 ? 'completed' : (o.status == 4 ? 'current' : '')}">
                                    <div class="order-step-icon"><i class="bi ${o.status > 4 ? 'bi-check-lg' : 'bi-bicycle'}"></i></div>
                                    <div class="order-step-label">Giao hàng</div>
                                </div>
                                <div class="order-step ${o.status == 5 ? 'completed' : ''}">
                                    <div class="order-step-icon"><i class="bi ${o.status == 5 ? 'bi-check-all' : 'bi-patch-check'}"></i></div>
                                    <div class="order-step-label">Đã giao</div>
                                </div>
                            </div>
                        </c:if>

                        <!-- Thông báo riêng nếu đơn hàng Hủy hoặc Hoàn -->
                        <c:if test="${o.status == 6}">
                            <div class="alert alert-danger py-2 px-3 mb-3 d-flex align-items-center rounded-3">
                                <i class="bi bi-x-circle-fill fs-4 me-2"></i>
                                <div><strong>Đơn hàng đã bị hủy.</strong> Nếu cần trợ giúp, xin vui lòng liên hệ bộ phận hỗ trợ khách hàng.</div>
                            </div>
                        </c:if>
                        <c:if test="${o.status == 7}">
                            <div class="alert alert-dark py-2 px-3 mb-3 d-flex align-items-center rounded-3">
                                <i class="bi bi-arrow-counterclockwise fs-4 me-2"></i>
                                <div><strong>Đơn hàng đã hoàn trả về kho.</strong> Tiền hàng sẽ được xử lý hoàn trả theo chính sách.</div>
                            </div>
                        </c:if>

                        <div class="row g-4">
                            <!-- Thông tin người nhận -->
                            <div class="col-lg-4 col-md-5 border-end-lg">
                                <h6 class="text-secondary text-uppercase fw-bold small mb-3">
                                    <i class="bi bi-geo-alt-fill text-danger me-1"></i>Địa chỉ nhận hàng
                                </h6>
                                <p class="mb-1 fw-bold text-dark">${o.fullName} <span class="text-muted fw-normal ms-1">(${o.phone})</span></p>
                                <p class="mb-2 text-secondary small">${o.address}</p>
                                <c:if test="${not empty o.note}">
                                    <p class="mb-2 small text-muted fst-italic">
                                        <i class="bi bi-chat-left-dots me-1"></i>Ghi chú: ${o.note}
                                    </p>
                                </c:if>
                                <div class="badge bg-light text-dark border p-2 mt-1">
                                    <i class="bi bi-cash-stack text-success me-1"></i>Phương thức: <strong>COD (Thanh toán khi nhận)</strong>
                                </div>
                            </div>

                            <!-- Danh sách sản phẩm -->
                            <div class="col-lg-8 col-md-7">
                                <h6 class="text-secondary text-uppercase fw-bold small mb-3">
                                    <i class="bi bi-bag-check-fill text-primary me-1"></i>Sản phẩm trong đơn
                                </h6>
                                <div class="table-responsive">
                                    <table class="table align-middle mb-0">
                                        <tbody>
                                            <c:forEach items="${o.details}" var="d">
                                                <tr>
                                                    <td style="width: 75px;" class="ps-0">
                                                        <c:choose>
                                                            <c:when test="${not empty d.poster}">
                                                                <img src="${pageContext.request.contextPath}/image?fname=${d.poster}" 
                                                                     alt="${d.videoName}" class="product-thumb">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class="product-thumb-placeholder">
                                                                    <i class="bi bi-play-circle"></i>
                                                                </div>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <h6 class="mb-0 fw-semibold text-dark">${d.videoName}</h6>
                                                        <small class="text-muted">Mã: ${d.videoId}</small>
                                                    </td>
                                                    <td class="text-center text-muted">
                                                        x${d.quantity}
                                                    </td>
                                                    <td class="text-end">
                                                        <span class="text-muted small"><fmt:formatNumber value="${d.price}" pattern="#,##0" />đ</span>
                                                    </td>
                                                    <td class="text-end fw-bold text-danger pe-0">
                                                        <fmt:formatNumber value="${d.totalPrice}" pattern="#,##0" />đ
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Card Footer -->
                    <div class="card-footer bg-white border-top py-3 px-4 d-flex flex-wrap justify-content-between align-items-center gap-3">
                        <div class="text-muted small">
                            Trạng thái chi tiết: <strong>${o.statusDescription}</strong>
                        </div>
                        <div class="d-flex align-items-center gap-3">
                            <div class="text-end">
                                <span class="text-muted small me-1">Tổng thanh toán:</span>
                                <span class="fs-5 fw-bold text-danger">
                                    <fmt:formatNumber value="${o.totalPrice}" pattern="#,##0" />đ
                                </span>
                            </div>
                            <!-- Nút Hủy đơn (Chỉ cho phép khi đơn ở trạng thái 0: Đơn hàng mới) -->
                            <c:if test="${o.status == 0}">
                                <a href="${pageContext.request.contextPath}/orders?action=cancel&orderId=${o.orderId}" 
                                   class="btn btn-outline-danger btn-sm px-3"
                                   onclick="return confirm('Bạn có chắc chắn muốn hủy đơn hàng #${o.orderId} không?');">
                                    <i class="bi bi-x-circle me-1"></i>Hủy đơn
                                </a>
                            </c:if>
                            <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-primary btn-sm px-3">
                                <i class="bi bi-arrow-repeat me-1"></i>Mua lại
                            </a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
