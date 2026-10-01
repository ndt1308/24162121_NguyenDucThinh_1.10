-- Chạy SAU KHI Tomcat đã start 1 lần (Hibernate tự tạo bảng). Chọn đúng database WebDe03.
USE WebDe03;
GO
INSERT INTO Users (Username, Password, Phone, Fullname, Email, Admin, Active, Images)
VALUES (N'admin', N'123', N'0900000001', N'Quản trị viên', N'admin@gmail.com', 1, 1, NULL),
       (N'user1', N'123', N'0900000002', N'Người dùng 1',  N'user1@gmail.com', 0, 1, NULL);
GO
