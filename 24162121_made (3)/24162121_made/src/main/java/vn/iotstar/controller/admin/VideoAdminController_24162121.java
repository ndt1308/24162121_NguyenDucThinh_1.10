package vn.iotstar.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import vn.iotstar.entity.Category_24162121;
import vn.iotstar.entity.Videos_24162121;
import vn.iotstar.service.ICategoryService_24162121;
import vn.iotstar.service.IVideoService_24162121;
import vn.iotstar.service.impl.CategoryServiceImpl_24162121;
import vn.iotstar.service.impl.VideoServiceImpl_24162121;
import vn.iotstar.util.FileUtil_24162121;
import vn.iotstar.util.PageResult_24162121;

/** CRUD Videos cho admin: danh sách (phân trang 6 video/trang), thêm, sửa, xóa. */
@WebServlet(urlPatterns = {"/admin/videos", "/admin/videos/add", "/admin/videos/edit", "/admin/videos/delete"})
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class VideoAdminController_24162121 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int PAGE_SIZE = 6;

    private final IVideoService_24162121 videoService = new VideoServiceImpl_24162121();
    private final ICategoryService_24162121 categoryService = new CategoryServiceImpl_24162121();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();
        String ctx = req.getContextPath();

        if (path.equals("/admin/videos/add")) {
            Videos_24162121 video = new Videos_24162121();
            video.setActive(true);
            video.setViews(0);
            showForm(req, resp, video, false);
        } else if (path.equals("/admin/videos/edit")) {
            Videos_24162121 video = videoService.findById(req.getParameter("id"));
            if (video == null) {
                resp.sendRedirect(ctx + "/admin/videos");
                return;
            }
            showForm(req, resp, video, true);
        } else if (path.equals("/admin/videos/delete")) {
            resp.sendRedirect(ctx + "/admin/videos");
        } else {
            list(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();
        if (path.equals("/admin/videos/add")) {
            save(req, resp, false);
        } else if (path.equals("/admin/videos/edit")) {
            save(req, resp, true);
        } else if (path.equals("/admin/videos/delete")) {
            videoService.delete(req.getParameter("id"));
            resp.sendRedirect(req.getContextPath() + "/admin/videos?page=" + parsePage(req.getParameter("page")) + "&msg=deleted");
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/videos");
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = parsePage(req.getParameter("page"));
        PageResult_24162121<Videos_24162121> pageResult = videoService.findPage(page, PAGE_SIZE);
        req.setAttribute("pageResult", pageResult);
        req.getRequestDispatcher("/WEB-INF/views/admin/videos.jsp").forward(req, resp);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp, Videos_24162121 video, boolean editing)
            throws ServletException, IOException {
        req.setAttribute("video", video);
        req.setAttribute("editing", editing);
        req.setAttribute("categories", categoryService.findAll());
        req.setAttribute("page", parsePage(req.getParameter("page")));
        req.getRequestDispatcher("/WEB-INF/views/admin/video-form.jsp").forward(req, resp);
    }

    private void save(HttpServletRequest req, HttpServletResponse resp, boolean editing)
            throws ServletException, IOException {
        int page = parsePage(req.getParameter("page"));

        Videos_24162121 video;
        if (editing) {
            video = videoService.findById(req.getParameter("videoId"));
            if (video == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/videos");
                return;
            }
        } else {
            video = new Videos_24162121();
        }

        String title = trim(req.getParameter("title"));
        String description = trim(req.getParameter("description"));
        String viewsText = trim(req.getParameter("views"));
        String priceText = trim(req.getParameter("price"));
        String stockText = trim(req.getParameter("stock"));
        String categoryText = trim(req.getParameter("categoryId"));
        boolean active = req.getParameter("active") != null;

        video.setTitle(title);
        video.setDescription(description);
        video.setActive(active);

        String error = null;
        int views = 0;
        long price = 0;
        int stock = 0;
        if (title.isEmpty()) {
            error = "Vui lòng nhập tiêu đề";
        } else if (title.length() > 200) {
            error = "Tiêu đề tối đa 200 ký tự";
        } else if (description.length() > 500) {
            error = "Mô tả tối đa 500 ký tự";
        } else {
            try {
                views = viewsText.isEmpty() ? 0 : Integer.parseInt(viewsText);
                price = priceText.isEmpty() ? 0 : Long.parseLong(priceText);
                stock = stockText.isEmpty() ? 0 : Integer.parseInt(stockText);
                if (views < 0) error = "Lượt xem không được âm";
                else if (price < 0) error = "Giá không được âm";
                else if (stock < 0) error = "Tồn kho không được âm";
            } catch (NumberFormatException e) {
                error = "Lượt xem, giá và tồn kho phải là số nguyên";
            }
        }
        video.setViews(views);
        video.setPrice(price);
        video.setStock(stock);

        if (error == null) {
            Category_24162121 category = null;
            try {
                category = categoryService.findById(Integer.parseInt(categoryText));
            } catch (NumberFormatException e) {
                category = null;
            }
            if (category == null) {
                error = "Vui lòng chọn Category";
            } else {
                video.setCategory(category);
            }
        }

        if (error == null) {
            try {
                String poster = FileUtil_24162121.save(req.getPart("poster"));
                if (poster != null) {
                    video.setPoster(poster);
                }
            } catch (IllegalStateException e) {
                error = "File ảnh quá lớn (tối đa 5MB)";
            } catch (IOException e) {
                error = e.getMessage();
            }
        }

        if (error != null) {
            req.setAttribute("error", error);
            showForm(req, resp, video, editing);
            return;
        }

        if (editing) {
            videoService.update(video);
        } else {
            videoService.insert(video);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/videos?page=" + (editing ? page : 1)
                + "&msg=" + (editing ? "updated" : "added"));
    }

    private int parsePage(String s) {
        try {
            int p = Integer.parseInt(s);
            return p < 1 ? 1 : p;
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    private String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
