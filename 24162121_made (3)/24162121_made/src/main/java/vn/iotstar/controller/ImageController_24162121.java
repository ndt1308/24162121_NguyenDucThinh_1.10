package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import vn.iotstar.util.FileUtil_24162121;

/** Trả ảnh poster đã upload:  /uploads/<tên file> */
@WebServlet("/uploads/*")
public class ImageController_24162121 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getPathInfo();
        if (name == null || name.length() < 2) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Path file = FileUtil_24162121.UPLOAD_DIR.resolve(name.substring(1)).normalize();
        if (!file.startsWith(FileUtil_24162121.UPLOAD_DIR) || !Files.isRegularFile(file)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String mime = getServletContext().getMimeType(file.getFileName().toString());
        resp.setContentType(mime != null ? mime : "application/octet-stream");
        resp.setContentLengthLong(Files.size(file));
        Files.copy(file, resp.getOutputStream());
    }
}
