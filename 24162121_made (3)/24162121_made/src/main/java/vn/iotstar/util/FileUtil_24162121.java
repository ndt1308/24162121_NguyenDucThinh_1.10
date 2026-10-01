package vn.iotstar.util;

import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileUtil_24162121 {
   
    public static final Path UPLOAD_DIR =
            Paths.get(System.getProperty("user.home"), "uploads_de03").toAbsolutePath().normalize();

    /** Lưu file ảnh, trả về tên file mới (tối đa 20 ký tự, vừa cột Poster nvarchar(50)). Không có file thì trả về null. */
    public static String save(Part part) throws IOException {
        if (part == null || part.getSize() == 0) {
            return null;
        }
        String submitted = part.getSubmittedFileName();
        String ext = "";
        int dot = submitted == null ? -1 : submitted.lastIndexOf('.');
        if (dot >= 0) {
            ext = submitted.substring(dot).toLowerCase();
        }
        if (!ext.matches("\\.(jpg|jpeg|png|gif|webp)")) {
            throw new IOException("Poster chỉ chấp nhận file ảnh jpg, png, gif, webp");
        }
        Files.createDirectories(UPLOAD_DIR);
        String name = UUID.randomUUID().toString().replace("-", "").substring(0, 16) + ext;
        try (InputStream in = part.getInputStream()) {
            Files.copy(in, UPLOAD_DIR.resolve(name), StandardCopyOption.REPLACE_EXISTING);
        }
        return name;
    }
}
