IF DB_ID(N'WebVideoDB') IS NULL
BEGIN
    CREATE DATABASE WebVideoDB;
END;
GO

USE WebVideoDB;
GO

-- Cho phép chạy lại file khi cần tạo mới dữ liệu kiểm thử.
IF OBJECT_ID(N'dbo.OrderDetails', N'U') IS NOT NULL DROP TABLE dbo.OrderDetails;
IF OBJECT_ID(N'dbo.Orders', N'U') IS NOT NULL DROP TABLE dbo.Orders;
IF OBJECT_ID(N'dbo.Favorites', N'U') IS NOT NULL DROP TABLE dbo.Favorites;
IF OBJECT_ID(N'dbo.Shares', N'U') IS NOT NULL DROP TABLE dbo.Shares;
IF OBJECT_ID(N'dbo.Videos', N'U') IS NOT NULL DROP TABLE dbo.Videos;
IF OBJECT_ID(N'dbo.Users', N'U') IS NOT NULL DROP TABLE dbo.Users;
IF OBJECT_ID(N'dbo.Category', N'U') IS NOT NULL DROP TABLE dbo.Category;
GO

CREATE TABLE dbo.Category (
    CategoryId INT IDENTITY(1,1) PRIMARY KEY,
    Categoryname NVARCHAR(100),
    Categorycode NVARCHAR(100),
    Images NVARCHAR(500),
    Status BIT DEFAULT 1
);

CREATE TABLE dbo.Users (
    Username NVARCHAR(50) PRIMARY KEY,
    Password NVARCHAR(255) NOT NULL,
    Phone NVARCHAR(15),
    Fullname NVARCHAR(255),
    Email NVARCHAR(255) UNIQUE,
    Admin BIT DEFAULT 0,
    Active BIT DEFAULT 0,
    Images NVARCHAR(500),
    Code VARCHAR(10) NULL
);

CREATE TABLE dbo.Videos (
    VideoId NVARCHAR(50) PRIMARY KEY,
    Title NVARCHAR(255) NOT NULL,
    Poster NVARCHAR(500),
    Views INT DEFAULT 0,
    Description NVARCHAR(MAX),
    Active BIT DEFAULT 1,
    CategoryId INT,
    CONSTRAINT FK_Videos_Category FOREIGN KEY (CategoryId)
        REFERENCES dbo.Category(CategoryId)
);

CREATE TABLE dbo.Shares (
    ShareId INT IDENTITY(1,1) PRIMARY KEY,
    Emails NVARCHAR(255),
    SharedDate DATETIME DEFAULT GETDATE(),
    Username NVARCHAR(50) NOT NULL,
    VideoId NVARCHAR(50) NOT NULL,
    CONSTRAINT FK_Shares_Users FOREIGN KEY (Username) REFERENCES dbo.Users(Username),
    CONSTRAINT FK_Shares_Videos FOREIGN KEY (VideoId) REFERENCES dbo.Videos(VideoId) ON DELETE CASCADE
);

CREATE TABLE dbo.Favorites (
    FavoriteId INT IDENTITY(1,1) PRIMARY KEY,
    LikedDate DATETIME DEFAULT GETDATE(),
    VideoId NVARCHAR(50) NOT NULL,
    Username NVARCHAR(50) NOT NULL,
    CONSTRAINT FK_Favorites_Users FOREIGN KEY (Username) REFERENCES dbo.Users(Username),
    CONSTRAINT FK_Favorites_Videos FOREIGN KEY (VideoId) REFERENCES dbo.Videos(VideoId) ON DELETE CASCADE,
    CONSTRAINT UQ_Favorites_User_Video UNIQUE (Username, VideoId)
);

CREATE TABLE dbo.Orders (
    OrderId INT IDENTITY(1,1) PRIMARY KEY,
    Username NVARCHAR(50) NOT NULL,
    FullName NVARCHAR(255) NOT NULL,
    Phone NVARCHAR(20) NOT NULL,
    Address NVARCHAR(500) NOT NULL,
    Note NVARCHAR(MAX),
    TotalPrice FLOAT NOT NULL,
    Status INT DEFAULT 0, -- 0: Đơn hàng mới, 1: Đã xác nhận, 2: Chuẩn bị hàng, 3: Vận chuyển, 4: Giao hàng, 5: Đã giao, 6: Đơn hàng hủy, 7: Đơn hàng hoàn
    CreatedAt DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_Orders_Users FOREIGN KEY (Username) REFERENCES dbo.Users(Username)
);

CREATE TABLE dbo.OrderDetails (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    OrderId INT NOT NULL,
    VideoId NVARCHAR(50) NOT NULL,
    VideoName NVARCHAR(255) NOT NULL,
    Poster NVARCHAR(500) NULL,
    Price FLOAT NOT NULL,
    Quantity INT NOT NULL,
    CONSTRAINT FK_OrderDetails_Orders FOREIGN KEY (OrderId) REFERENCES dbo.Orders(OrderId) ON DELETE CASCADE
);
GO

INSERT INTO dbo.Users (Username, Password, Phone, Fullname, Email, Admin, Active)
VALUES
(N'admin', N'123', N'0901234567', N'Quản trị viên', N'admin@gmail.com', 1, 1),
(N'user1', N'123', N'0912345678', N'Nguyễn Văn A', N'user1@gmail.com', 0, 1);

INSERT INTO dbo.Category (Categoryname, Categorycode, Images, Status)
VALUES
(N'Áo Bóng Đá Thể Thao', N'AO_BONG_DA', N'aobongda_nam_BDN.webp', 1),
(N'Giày Đá Bóng & Phụ Kiện', N'GIAY_BONG_DA', N'GIAYBONGDA_VAPOR16.webp', 1),
(N'Công Nghệ & Giải Trí', N'CONG_NGHE', N'laptop.jpg', 1);

INSERT INTO dbo.Videos (VideoId, Title, Poster, Price, Views, Description, Active, CategoryId)
VALUES
(N'VID01', N'Áo Đấu Bồ Đào Nha Ronaldo CR7', N'aobongda_nam_ronaldo.jpg', 250000, 1500, N'Chất liệu thun mè thể thao co giãn 4 chiều, thoáng khí, in ấn sắc nét.', 1, 1),
(N'VID02', N'Áo Đấu Đội Tuyển Bồ Đào Nha Sân Nhà', N'aobongda_nam_BDN.webp', 220000, 800, N'Màu sắc chuẩn thi đấu, thấm hút mồ hôi cực tốt khi vận động cường độ cao.', 1, 1),
(N'VID03', N'Áo Đấu Đội Tuyển Argentina Vô Địch', N'aobongda_nam_argentina.webp', 240000, 2300, N'Phiên bản 3 sao vô địch World Cup, form dáng chuẩn thể thao năng động.', 1, 1),
(N'VID04', N'Áo Đấu Argentina Phiên Bản Đen Vàng', N'aobongda_nam_argentina_den.jpg', 260000, 990, N'Thiết kế đen phối vàng gold sang trọng, chất vải dệt kim cao cấp.', 1, 1),
(N'VID05', N'Quả Bóng Đá World Cup 2026 Cao Cấp', N'quabong_wc26.webp', 350000, 1200, N'Bóng dán nhiệt không đường chỉ, giữ hơi cực lâu, độ nảy tiêu chuẩn FIFA.', 1, 2),
(N'VID06', N'Giày Đá Bóng Nike Mercurial Vapor 16', N'GIAYBONGDA_VAPOR16.webp', 850000, 3200, N'Đệm khí Zoom Air êm ái, bám sân cực tốt cho tiền đạo tốc độ.', 1, 2),
(N'VID07', N'Giày Đá Bóng Sân Cỏ Nhân Tạo Elite 17', N'GIAYBONGDA17.webp', 780000, 1400, N'Đế đinh dăm TF bám sân chống trơn trượt hiệu quả, da mềm ôm chân.', 1, 2),
(N'VID08', N'Giày Đá Bóng Vapor 16 Xanh Nhám', N'giaybongda16vapor_xanh4nham.webp', 920000, 2100, N'Phối màu xanh nhám thời thượng, trọng lượng siêu nhẹ giúp bứt tốc.', 1, 2),
(N'VID09', N'Điện Thoại Thông Minh Smartphone 5G', N'phone.jpg', 4990000, 450, N'Màn hình AMOLED 120Hz sắc nét, camera 64MP chụp đêm ấn tượng, pin trâu 5000mAh.', 1, 3),
(N'VID10', N'Laptop Văn Phòng Doanh Nhân Mỏng Nhẹ', N'laptop.jpg', 12500000, 780, N'CPU Intel thế hệ mới, RAM 16GB mượt mà, vỏ kim loại nguyên khối sang trọng.', 1, 3),
(N'VID11', N'Bom Tấn Avatar: Dòng Chảy Của Nước', N'posterAVATAR.jpg', 89000, 4200, N'Siêu phẩm điện ảnh 3D đỉnh cao của James Cameron, kỹ xảo mãn nhãn.', 1, 3);

INSERT INTO dbo.Favorites (LikedDate, VideoId, Username)
VALUES (GETDATE(), N'VID01', N'user1'), (GETDATE(), N'VID01', N'admin'), (GETDATE(), N'VID02', N'user1');

INSERT INTO dbo.Shares (Emails, SharedDate, Username, VideoId)
VALUES (N'friend@gmail.com', GETDATE(), N'user1', N'VID01');
GO

-- =========================================================================
-- DỮ LIỆU MẪU ĐƠN HÀNG VỚI ĐẦY ĐỦ 8 TRẠNG THÁI (CHO USER1 VÀ ADMIN)
-- 0: Đơn hàng mới
-- 1: Đã xác nhận
-- 2: Chuẩn bị hàng
-- 3: Vận chuyển
-- 4: Giao hàng
-- 5: Đã giao
-- 6: Đơn hàng hủy
-- 7: Đơn hàng hoàn
-- =========================================================================

-- 1. Đơn hàng mới (Status = 0)
INSERT INTO dbo.Orders (Username, FullName, Phone, Address, Note, TotalPrice, Status, CreatedAt)
VALUES (N'user1', N'Nguyễn Văn A', N'0912345678', N'1 Võ Văn Ngân, TP. Thủ Đức, TP. HCM', N'Giao trong giờ hành chính', 250000, 0, DATEADD(MINUTE, -10, GETDATE()));
INSERT INTO dbo.OrderDetails (OrderId, VideoId, VideoName, Poster, Price, Quantity)
VALUES (SCOPE_IDENTITY(), N'VID01', N'Áo Đấu Bồ Đào Nha Ronaldo CR7', N'aobongda_nam_ronaldo.jpg', 250000, 1);

-- 2. Đã xác nhận (Status = 1)
INSERT INTO dbo.Orders (Username, FullName, Phone, Address, Note, TotalPrice, Status, CreatedAt)
VALUES (N'user1', N'Nguyễn Văn A', N'0912345678', N'1 Võ Văn Ngân, TP. Thủ Đức, TP. HCM', N'Giao hàng nhanh giúp mình', 440000, 1, DATEADD(HOUR, -2, GETDATE()));
INSERT INTO dbo.OrderDetails (OrderId, VideoId, VideoName, Poster, Price, Quantity)
VALUES (SCOPE_IDENTITY(), N'VID02', N'Áo Đấu Đội Tuyển Bồ Đào Nha Sân Nhà', N'aobongda_nam_BDN.webp', 220000, 2);

-- 3. Chuẩn bị hàng (Status = 2)
INSERT INTO dbo.Orders (Username, FullName, Phone, Address, Note, TotalPrice, Status, CreatedAt)
VALUES (N'user1', N'Nguyễn Văn A', N'0912345678', N'1 Võ Văn Ngân, TP. Thủ Đức, TP. HCM', N'Đóng gói cẩn thận', 500000, 2, DATEADD(HOUR, -5, GETDATE()));
DECLARE @OrderId3 INT = SCOPE_IDENTITY();
INSERT INTO dbo.OrderDetails (OrderId, VideoId, VideoName, Poster, Price, Quantity)
VALUES (@OrderId3, N'VID03', N'Áo Đấu Đội Tuyển Argentina Vô Địch', N'aobongda_nam_argentina.webp', 240000, 1),
       (@OrderId3, N'VID04', N'Áo Đấu Argentina Phiên Bản Đen Vàng', N'aobongda_nam_argentina_den.jpg', 260000, 1);

-- 4. Vận chuyển (Status = 3)
INSERT INTO dbo.Orders (Username, FullName, Phone, Address, Note, TotalPrice, Status, CreatedAt)
VALUES (N'user1', N'Nguyễn Văn A', N'0912345678', N'1 Võ Văn Ngân, TP. Thủ Đức, TP. HCM', N'', 350000, 3, DATEADD(DAY, -1, GETDATE()));
INSERT INTO dbo.OrderDetails (OrderId, VideoId, VideoName, Poster, Price, Quantity)
VALUES (SCOPE_IDENTITY(), N'VID05', N'Quả Bóng Đá World Cup 2026 Cao Cấp', N'quabong_wc26.webp', 350000, 1);

-- 5. Giao hàng (Status = 4)
INSERT INTO dbo.Orders (Username, FullName, Phone, Address, Note, TotalPrice, Status, CreatedAt)
VALUES (N'user1', N'Nguyễn Văn A', N'0912345678', N'1 Võ Văn Ngân, TP. Thủ Đức, TP. HCM', N'Gọi trước khi giao 15 phút', 850000, 4, DATEADD(DAY, -2, GETDATE()));
INSERT INTO dbo.OrderDetails (OrderId, VideoId, VideoName, Poster, Price, Quantity)
VALUES (SCOPE_IDENTITY(), N'VID06', N'Giày Đá Bóng Nike Mercurial Vapor 16', N'GIAYBONGDA_VAPOR16.webp', 850000, 1);

-- 6. Đã giao (Status = 5)
INSERT INTO dbo.Orders (Username, FullName, Phone, Address, Note, TotalPrice, Status, CreatedAt)
VALUES (N'user1', N'Nguyễn Văn A', N'0912345678', N'1 Võ Văn Ngân, TP. Thủ Đức, TP. HCM', N'Hàng nhận đúng mô tả', 1560000, 5, DATEADD(DAY, -3, GETDATE()));
INSERT INTO dbo.OrderDetails (OrderId, VideoId, VideoName, Poster, Price, Quantity)
VALUES (SCOPE_IDENTITY(), N'VID07', N'Giày Đá Bóng Sân Cỏ Nhân Tạo Elite 17', N'GIAYBONGDA17.webp', 780000, 2);

-- 7. Đơn hàng hủy (Status = 6)
INSERT INTO dbo.Orders (Username, FullName, Phone, Address, Note, TotalPrice, Status, CreatedAt)
VALUES (N'user1', N'Nguyễn Văn A', N'0912345678', N'1 Võ Văn Ngân, TP. Thủ Đức, TP. HCM', N'Đổi ý không mua nữa', 920000, 6, DATEADD(DAY, -4, GETDATE()));
INSERT INTO dbo.OrderDetails (OrderId, VideoId, VideoName, Poster, Price, Quantity)
VALUES (SCOPE_IDENTITY(), N'VID08', N'Giày Đá Bóng Vapor 16 Xanh Nhám', N'giaybongda16vapor_xanh4nham.webp', 920000, 1);

-- 8. Đơn hàng hoàn (Status = 7)
INSERT INTO dbo.Orders (Username, FullName, Phone, Address, Note, TotalPrice, Status, CreatedAt)
VALUES (N'user1', N'Nguyễn Văn A', N'0912345678', N'1 Võ Văn Ngân, TP. Thủ Đức, TP. HCM', N'Không vừa size, hoàn hàng đổi sản phẩm', 4990000, 7, DATEADD(DAY, -5, GETDATE()));
INSERT INTO dbo.OrderDetails (OrderId, VideoId, VideoName, Poster, Price, Quantity)
VALUES (SCOPE_IDENTITY(), N'VID09', N'Điện Thoại Thông Minh Smartphone 5G', N'phone.jpg', 4990000, 1);
GO

-- =========================================================================
-- CÂU LỆNH HƯỚNG DẪN KIỂM THỬ THAY ĐỔI TRẠNG THÁI ĐƠN HÀNG (MSSV: 24110165):
-- (Mở SSMS và chạy câu lệnh dưới đây để quan sát đơn hàng đổi trạng thái trên giao diện web):
--
-- UPDATE dbo.Orders SET Status = 0 WHERE OrderId = 1; -- Đổi thành 0: Đơn hàng mới
-- UPDATE dbo.Orders SET Status = 1 WHERE OrderId = 1; -- Đổi thành 1: Đã xác nhận
-- UPDATE dbo.Orders SET Status = 2 WHERE OrderId = 1; -- Đổi thành 2: Chuẩn bị hàng
-- UPDATE dbo.Orders SET Status = 3 WHERE OrderId = 1; -- Đổi thành 3: Vận chuyển
-- UPDATE dbo.Orders SET Status = 4 WHERE OrderId = 1; -- Đổi thành 4: Giao hàng
-- UPDATE dbo.Orders SET Status = 5 WHERE OrderId = 1; -- Đổi thành 5: Đã giao
-- UPDATE dbo.Orders SET Status = 6 WHERE OrderId = 1; -- Đổi thành 6: Đơn hàng hủy
-- UPDATE dbo.Orders SET Status = 7 WHERE OrderId = 1; -- Đổi thành 7: Đơn hàng hoàn
-- =========================================================================

