-- Dữ liệu demo cho trang Home (Câu 4, 5): 4 category, mỗi category 20 video => hiện "Tên (20)" và phân trang 3 video/trang.
-- Không đụng tới bảng Users. Chạy nhiều lần được (xóa Favorites/Shares/Videos rồi tạo lại).
USE WebDe03;
GO

DELETE FROM Favorites;
DELETE FROM Shares;
DELETE FROM Videos;

-- Đảm bảo có đủ 4 category
INSERT INTO Category (Categoryname, Categorycode, Images, Status)
SELECT v.n, v.c, v.i, 1
FROM (VALUES
  (N'Âm nhạc',  N'MUSIC', N'https://picsum.photos/seed/cat1/300/200'),
  (N'Phim ảnh', N'MOVIE', N'https://picsum.photos/seed/cat2/300/200'),
  (N'Thể thao', N'SPORT', N'https://picsum.photos/seed/cat3/300/200'),
  (N'Giáo dục', N'EDU',   N'https://picsum.photos/seed/cat4/300/200')
) AS v(n, c, i)
WHERE NOT EXISTS (SELECT 1 FROM Category WHERE Categorycode = v.c);

-- 10 user mẫu để có dữ liệu Like/Share
DECLARE @u INT = 1, @un NVARCHAR(50);
WHILE @u <= 10
BEGIN
    SET @un = CONCAT(N'sv', RIGHT(CONCAT('00', @u), 2));
    IF NOT EXISTS (SELECT 1 FROM Users WHERE Username = @un)
        INSERT INTO Users (Username, Password, Phone, Fullname, Email, Admin, Active, Images)
        VALUES (@un, N'123', N'0900000000', CONCAT(N'Sinh viên ', @u), CONCAT(@un, N'@example.com'), 0, 1, NULL);
    SET @u = @u + 1;
END

-- 80 video: chia đều cho 4 category (mỗi category 20 video)
DECLARE @i INT = 1, @j INT, @vid NVARCHAR(50), @cat INT, @likes INT, @shares INT;
WHILE @i <= 80
BEGIN
    SET @vid = CONCAT(N'VD', RIGHT(CONCAT('0000', @i), 4));
    SELECT @cat = CategoryId
      FROM (SELECT TOP 4 CategoryId, ROW_NUMBER() OVER (ORDER BY CategoryId) AS rn FROM Category ORDER BY CategoryId) c
     WHERE rn = ((@i - 1) % 4) + 1;

    INSERT INTO Videos (VideoId, Title, Poster, Views, Description, Active, CategoryId)
    VALUES (@vid, CONCAT(N'Video số ', @i),
            CONCAT(N'https://picsum.photos/seed/vd', @i, N'/400/250'),
            @i * 100, CONCAT(N'Đây là mô tả của video số ', @i, N'.'), 1, @cat);

    SET @likes  = (@i % 10) + 1;
    SET @shares = ((@i * 3) % 8) + 1;

    SET @j = 1;
    WHILE @j <= @likes
    BEGIN
        INSERT INTO Favorites (LikedDate, VideoId, Username)
        VALUES (CAST(GETDATE() AS date), @vid, CONCAT(N'sv', RIGHT(CONCAT('00', @j), 2)));
        SET @j = @j + 1;
    END

    SET @j = 1;
    WHILE @j <= @shares
    BEGIN
        INSERT INTO Shares (Emails, SharedDate, Username, VideoId)
        VALUES (CONCAT(N'friend', @j, N'@example.com'), CAST(GETDATE() AS date),
                CONCAT(N'sv', RIGHT(CONCAT('00', @j), 2)), @vid);
        SET @j = @j + 1;
    END
    SET @i = @i + 1;
END
GO

-- Kiểm tra Câu 5: số video theo từng category
SELECT c.CategoryId, c.Categoryname, COUNT(v.VideoId) AS SoVideo
FROM Category c LEFT JOIN Videos v ON v.CategoryId = c.CategoryId
GROUP BY c.CategoryId, c.Categoryname ORDER BY c.CategoryId;
GO
