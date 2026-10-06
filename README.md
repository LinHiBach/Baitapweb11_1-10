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

### 3. Chức năng Lịch sử đặt hàng & Lọc theo trạng thái (`/orders`)
- **Truy cập**: Bấm vào nút **"Lịch sử đơn"** trên thanh Menu Navbar (hoặc sau khi đặt hàng thành công tại `/order-success`).
- **Lọc theo 8 trạng thái tiêu chuẩn**:
  - Giao diện cung cấp thanh Tab chọn trạng thái trực quan với số lượng đếm thực tế `(Badge count)` cho từng trạng thái:
    1. **Tất cả**: Xem toàn bộ đơn hàng của người dùng.
    2. **Đơn hàng mới** (`Status = 0`): Đơn vừa đặt, đang chờ hệ thống tiếp nhận. Có nút **Hủy đơn hàng**.
    3. **Đã xác nhận** (`Status = 1`): Đơn đã được cửa hàng xác nhận.
    4. **Chuẩn bị hàng** (`Status = 2`): Đang đóng gói kiện hàng.
    5. **Vận chuyển** (`Status = 3`): Kiện hàng đã giao cho bưu cục và đang luân chuyển.
    6. **Giao hàng** (`Status = 4`): Shipper đang giao đến người nhận.
    7. **Đã giao** (`Status = 5`): Giao hàng và thu hộ tiền mặt (COD) thành công.
    8. **Đơn hàng hủy** (`Status = 6`): Đơn hàng đã bị hủy.
    9. **Đơn hàng hoàn** (`Status = 7`): Đơn hàng hoàn trả về kho.
- **Thanh tiến trình (Order Stepper Timeline)**: Hiển thị trực quan từng chặng tiến độ đơn hàng từ Đơn mới -> Đã giao.
- **Dữ liệu chi tiết**: Hiển thị ảnh sản phẩm, tên, số lượng, giá, tổng tiền, thông tin người nhận, địa chỉ và ghi chú.

---

## 🧪 HƯỚNG DẪN KIỂM THỬ THAY ĐỔI TRẠNG THÁI TRONG DATABASE

Theo yêu cầu: *(vào database để thay đổi các trạng thái để quan sát trạng thái đơn thay đổi theo trạng thái tương ứng)*:

### 1. Bảng quy ước mã trạng thái trong CSDL (`dbo.Orders.Status`):
| Mã Status (INT) | Trạng thái hiển thị | Ý nghĩa tiến trình |
| :---: | :--- | :--- |
| `0` | **Đơn hàng mới** | Vừa đặt xong (Mặc định khi checkout) |
| `1` | **Đã xác nhận** | Cửa hàng duyệt đơn |
| `2` | **Chuẩn bị hàng** | Đang đóng gói |
| `3` | **Vận chuyển** | Xuất kho, luân chuyển bưu cục |
| `4` | **Giao hàng** | Shipper đang đi phát hàng |
| `5` | **Đã giao** | Khách đã nhận & trả tiền COD |
| `6` | **Đơn hàng hủy** | Đơn bị hủy |
| `7` | **Đơn hàng hoàn** | Đơn bị hoàn trả |

### 2. Các câu lệnh SQL kiểm thử trong SQL Server Management Studio (SSMS):
```sql
USE WebVideoDB;
GO

-- Xem danh sách tất cả các đơn hàng hiện có:
SELECT OrderId, Username, FullName, TotalPrice, Status, CreatedAt FROM dbo.Orders ORDER BY OrderId;

-- Ví dụ: Đổi trạng thái của đơn hàng #1 để quan sát trên web (/orders):
UPDATE dbo.Orders SET Status = 0 WHERE OrderId = 1; -- 0: Đơn hàng mới
UPDATE dbo.Orders SET Status = 1 WHERE OrderId = 1; -- 1: Đã xác nhận
UPDATE dbo.Orders SET Status = 2 WHERE OrderId = 1; -- 2: Chuẩn bị hàng
UPDATE dbo.Orders SET Status = 3 WHERE OrderId = 1; -- 3: Vận chuyển
UPDATE dbo.Orders SET Status = 4 WHERE OrderId = 1; -- 4: Giao hàng
UPDATE dbo.Orders SET Status = 5 WHERE OrderId = 1; -- 5: Đã giao
UPDATE dbo.Orders SET Status = 6 WHERE OrderId = 1; -- 6: Đơn hàng hủy
UPDATE dbo.Orders SET Status = 7 WHERE OrderId = 1; -- 7: Đơn hàng hoàn
```
> 💡 *Sau khi chạy lệnh `UPDATE` trong SSMS, tải lại trang `http://localhost:8080/DEMO/orders` hoặc bấm qua các Tab lọc tương ứng để quan sát đơn hàng tự động nhảy vào đúng nhóm trạng thái!*

### 3. Quản lý trạng thái từ Trang Quản Trị Admin (`/admin/orders`):
- Đăng nhập tài khoản `admin` / `123`.
- Vào menu **Quản lý Đơn hàng** (`/admin/orders`).
- Lọc theo từng trạng thái và có thể chọn trạng thái mới trong dropdown rồi bấm **Lưu** trực tiếp trên website.

---

## 🛠️ HƯỚNG DẪN CÀI ĐẶT & CHẠY DỰ ÁN

### 1. Cơ sở dữ liệu (SQL Server)
- Mở **SQL Server Management Studio (SSMS)**.
- Mở và thực thi toàn bộ file script: [`24110165_de3.sql`](./24110165_de3.sql).
- File script đã tạo sẵn dữ liệu mẫu cho cả 8 trạng thái đơn hàng để kiểm thử ngay lập tức.
- Kiểm tra cấu hình kết nối trong file: `src/main/resources/META-INF/persistence.xml` (Port: `1433`, User: `sa`, Database: `WebVideoDB`).

### 2. Triển khai Server (Tomcat)
- Mở project trong **Spring Tool Suite (STS)** hoặc **Eclipse**.
- Chuột phải vào project chọn **Maven** -> **Update Project...**
- Thêm project vào máy chủ **Apache Tomcat 10.1+** (hỗ trợ Jakarta EE 10 / Servlet 6.0).
- Khởi động máy chủ và truy cập: `http://localhost:8080/DEMO/home` hoặc `http://localhost:8080/DEMO/login`.

