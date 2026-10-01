-- Đặt giá + tồn kho cho các video đang có (chạy SAU KHI Tomcat start ít nhất 1 lần để Hibernate thêm cột Price, Stock).
-- Chạy lại seed_home_demo.sql / seed_data.sql thì phải chạy lại file này.
USE WebDe03;
GO
UPDATE Videos
SET Price = (ABS(CHECKSUM(NEWID())) % 20 + 5) * 10000,   -- 50.000 - 240.000 đ
    Stock = ABS(CHECKSUM(NEWID())) % 10 + 3              -- 3 - 12 sản phẩm
WHERE Price IS NULL OR Stock IS NULL;
GO

-- Xem đơn hàng và đổi trạng thái để quan sát (viết đúng chữ HOA):
-- NEW, CONFIRMED, PREPARING, SHIPPING, DELIVERING, DELIVERED, CANCELLED, RETURNED
-- SELECT OrderId, Username, Status, TotalAmount FROM Orders;
-- UPDATE Orders SET Status = 'CONFIRMED' WHERE OrderId = 1;
