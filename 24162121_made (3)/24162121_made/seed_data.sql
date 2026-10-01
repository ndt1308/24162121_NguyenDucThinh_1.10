-- Dữ liệu mẫu cho Category, Videos, Shares, Favorites.
-- Chạy SAU KHI Tomcat đã start ít nhất 1 lần (Hibernate đã tạo bảng). Có thể chạy lại nhiều lần.
USE WebDe03;
GO

-- Xóa dữ liệu cũ của 4 bảng này (KHÔNG đụng tới bảng Users thật của bạn)
DELETE FROM Favorites;
DELETE FROM Shares;
DELETE FROM Videos;
DELETE FROM Category;

-- 4 category
INSERT INTO Category (Categoryname, Categorycode, Images, Status) VALUES
(N'Âm nhạc',   N'MUSIC',  N'https://picsum.photos/seed/cat1/300/200', 1),
(N'Phim ảnh',  N'MOVIE',  N'https://picsum.photos/seed/cat2/300/200', 1),
(N'Thể thao',  N'SPORT',  N'https://picsum.photos/seed/cat3/300/200', 1),
(N'Giáo dục',  N'EDU',    N'https://picsum.photos/seed/cat4/300/200', 1);

-- 10 user mẫu (sv01..sv10) để làm dữ liệu Like/Share
DECLARE @u INT = 1;
DECLARE @un NVARCHAR(50);
WHILE @u <= 10
BEGIN
    SET @un = CONCAT(N'sv', RIGHT(CONCAT('00', @u), 2));
    IF NOT EXISTS (SELECT 1 FROM Users WHERE Username = @un)
        INSERT INTO Users (Username, Password, Phone, Fullname, Email, Admin, Active, Images)
        VALUES (@un, N'123', N'0900000000', CONCAT(N'Sinh viên ', @u), CONCAT(@un, N'@example.com'), 0, 1, NULL);
    SET @u = @u + 1;
END

-- 24 video (chia đều 4 category, mỗi category 6 video)
DECLARE @i INT = 1;
DECLARE @j INT;
DECLARE @vid NVARCHAR(50);
DECLARE @cat INT;
DECLARE @likes INT;
DECLARE @shares INT;
WHILE @i <= 24
BEGIN
    SET @vid = CONCAT(N'VD', RIGHT(CONCAT('0000', @i), 4));
    SELECT @cat = CategoryId
      FROM (SELECT CategoryId, ROW_NUMBER() OVER (ORDER BY CategoryId) AS rn FROM Category) c
     WHERE rn = ((@i - 1) % 4) + 1;

    INSERT INTO Videos (VideoId, Title, Poster, Views, Description, Active, CategoryId)
    VALUES (@vid,
            CONCAT(N'Video số ', @i),
            CONCAT(N'https://picsum.photos/seed/vd', @i, N'/400/250'),
            @i * 100,
            CONCAT(N'Đây là mô tả của video số ', @i, N'.'),
            1,
            @cat);

    SET @likes  = (@i % 10) + 1;        -- 1..10 lượt Like
    SET @shares = ((@i * 3) % 8) + 1;   -- 1..8 lượt Share

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

SELECT (SELECT COUNT(*) FROM Category)  AS SoCategory,
       (SELECT COUNT(*) FROM Videos)    AS SoVideo,
       (SELECT COUNT(*) FROM Favorites) AS SoLike,
       (SELECT COUNT(*) FROM Shares)    AS SoShare;
