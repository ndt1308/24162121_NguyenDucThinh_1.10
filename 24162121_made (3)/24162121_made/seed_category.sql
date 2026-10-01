-- Chỉ thêm 4 category (KHÔNG xóa dữ liệu khác). Chạy nhiều lần cũng không bị trùng.
USE WebDe03;
GO

INSERT INTO Category (Categoryname, Categorycode, Images, Status)
SELECT v.n, v.c, v.i, 1
FROM (VALUES
  (N'Âm nhạc',  N'MUSIC', N'https://picsum.photos/seed/cat1/300/200'),
  (N'Phim ảnh', N'MOVIE', N'https://picsum.photos/seed/cat2/300/200'),
  (N'Thể thao', N'SPORT', N'https://picsum.photos/seed/cat3/300/200'),
  (N'Giáo dục', N'EDU',   N'https://picsum.photos/seed/cat4/300/200')
) AS v(n, c, i)
WHERE NOT EXISTS (SELECT 1 FROM Category WHERE Categorycode = v.c);
GO

SELECT * FROM Category;
