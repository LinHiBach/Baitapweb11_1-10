# BÀI TẬP : HỆ THỐNG QUẢN LÝ VIDEO & BÁN HÀNG TRỰC TUYẾN

- **Họ và tên**: Lâm Huy Bách
- **MSSV**: 24110165
- **Mã đề**: Đề số 3
- **Công nghệ**: Java EE (Jakarta EE Servlet 6.0, JPA / Hibernate, JSP, JSTL, SiteMesh, Bootstrap 5)
- **Hệ quản trị CSDL**: Microsoft SQL Server

---

## 🔑 DANH SÁCH TÀI KHOẢN KIỂM THỬ (TEST ACCOUNTS)

Dưới đây là các tài khoản đã được thiết lập sẵn trong cơ sở dữ liệu `WebVideoDB` để kiểm tra phân quyền và chức năng:

| Vai trò (Role) | Tên đăng nhập (Username) | Mật khẩu (Password) | Chuyển hướng sau đăng nhập | Quyền hạn & Chức năng kiểm thử |
| :--- | :--- | :--- | :--- | :--- |
| **Quản trị viên (Admin)** | `admin` | `123` | `/admin/home` | - Quản lý danh mục (Category)<br>- Quản lý video & sản phẩm (Thêm/Sửa/Xóa)<br>- Xem thống kê lượt xem, yêu thích, chia sẻ |
| **Người dùng (User)** | `user1` | `123` | `/home` | - Xem sản phẩm, chi tiết, đơn giá<br>- **Giỏ hàng**: Thêm, xóa, cập nhật số lượng (1 - 10)<br>- **Thanh toán**: Đặt hàng COD (nhận hàng trả tiền), xem hóa đơn thành công |

> 💡 **Ghi chú**: Nếu đăng ký tài khoản mới qua form `/register`, tài khoản cần nhập đúng mã OTP gửi về email để kích hoạt trạng thái trước khi đăng nhập.

---

## 🛒 CÁC CHỨC NĂNG DÀNH CHO VAI TRÒ USER

### 1. Chức năng Giỏ hàng (`/cart`)
- **Thêm vào giỏ**: Bấm "Thêm giỏ" từ Trang chủ (`/home`) hoặc Trang chi tiết (`/video/detail`).
- **Lưu trữ Session**: Giỏ hàng lưu an toàn trong HTTP Session (`Map<String, CartItem_24110165>`), hiển thị số lượng badge màu đỏ trên thanh Navbar.
- **Thay đổi số lượng có giới hạn**:
  - Nút `[-]`: Giảm số lượng (chặn tối thiểu mức `1`).
  - Nút `[+]`: Tăng số lượng (chặn tối đa mức `10`).
- **Xóa sản phẩm**:
  - Xóa từng món bằng icon thùng rác (kèm popup xác nhận).
  - Nút "Xóa tất cả" để làm sạch giỏ hàng.
- **Tính toán tự động**: Tự động tính thành tiền từng món và tổng tiền cả giỏ hàng theo thời gian thực.

### 2. Chức năng Thanh toán đơn hàng bằng COD (`/order`)
- **Kiểm tra đăng nhập**: Tự động yêu cầu đăng nhập nếu người dùng chưa xác thực.
- **Form Checkout**:
  - Tự động điền trước Họ tên và Số điện thoại từ tài khoản đăng nhập.
  - Nhập Địa chỉ giao hàng nhận hàng và Ghi chú đơn hàng.
  - Chọn phương thức: **Thanh toán tiền mặt khi nhận hàng (COD)**.
- **Xử lý đơn hàng**:
  - Lưu vào bảng `Orders` và chi tiết các món vào bảng `OrderDetails`.
  - Tự động làm sạch giỏ hàng sau khi đặt thành công.
- **Trang xác nhận (`/views/web/order-success.jsp`)**:
  - Hiển thị mã đơn hàng `#ID`, thông tin người nhận, danh sách sản phẩm và tổng tiền thanh toán COD.

---

## 🛠️ HƯỚNG DẪN CÀI ĐẶT & CHẠY DỰ ÁN

### 1. Cơ sở dữ liệu (SQL Server)
- Mở **SQL Server Management Studio (SSMS)**.
- Mở và thực thi toàn bộ file script: [`24110165_de3.sql`](./24110165_de3.sql).
- Kiểm tra cấu hình kết nối trong file: `src/main/resources/META-INF/persistence.xml` (Port: `1433`, User: `sa`, Database: `WebVideoDB`).

### 2. Triển khai Server (Tomcat)
- Mở project trong **Spring Tool Suite (STS)** hoặc **Eclipse**.
- Chuột phải vào project chọn **Maven** -> **Update Project...**
- Thêm project vào máy chủ **Apache Tomcat 10.1+** (hỗ trợ Jakarta EE 10 / Servlet 6.0).
- Khởi động máy chủ và truy cập: `http://localhost:8080/DEMO/home` hoặc `http://localhost:8080/DEMO/login`.
