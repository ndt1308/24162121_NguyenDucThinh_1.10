package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import vn.iotstar.dto.CategoryVideosDTO_24162121;
import vn.iotstar.entity.Category_24162121;
import vn.iotstar.entity.Videos_24162121;
import vn.iotstar.service.ICategoryService_24162121;
import vn.iotstar.service.IVideoService_24162121;
import vn.iotstar.service.impl.CategoryServiceImpl_24162121;
import vn.iotstar.service.impl.VideoServiceImpl_24162121;
import vn.iotstar.util.PageResult_24162121;

/**
 * Câu 4: trang home của user - hiển thị video theo từng Category, mỗi Category phân trang 3 video/trang.
 * Câu 5: đếm số video của từng Category, hiển thị ngay cạnh tên Category.
 * Mỗi Category có tham số trang riêng: ?page_1=2&page_3=4 ...
 */
@WebServlet(urlPatterns = {"/home", "/products"})
public class HomeController_24162121 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int PAGE_SIZE = 3;

    private final ICategoryService_24162121 categoryService = new CategoryServiceImpl_24162121();
    private final IVideoService_24162121 videoService = new VideoServiceImpl_24162121();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Category_24162121> categories = categoryService.findAll();
        Map<Integer, Long> counts = videoService.countGroupByCategory();   // Câu 5

        // Trang hiện tại của từng category (mặc định 1)
        Map<Integer, Integer> currentPages = new LinkedHashMap<>();
        for (Category_24162121 c : categories) {
            currentPages.put(c.getCategoryId(), parsePage(req.getParameter("page_" + c.getCategoryId())));
        }

        List<CategoryVideosDTO_24162121> blocks = new ArrayList<>();
        for (Category_24162121 c : categories) {
            int cid = c.getCategoryId();
            PageResult_24162121<Videos_24162121> pr = videoService.findPageByCategory(cid, currentPages.get(cid), PAGE_SIZE);

            Map<String, Long> shares = new HashMap<>();
            Map<String, Long> likes = new HashMap<>();
            for (Videos_24162121 v : pr.getItems()) {
                shares.put(v.getVideoId(), videoService.countShares(v.getVideoId()));
                likes.put(v.getVideoId(), videoService.countLikes(v.getVideoId()));
            }

            // Giữ nguyên trang của các category khác khi bấm chuyển trang ở category này
            StringBuilder other = new StringBuilder();
            for (Category_24162121 o : categories) {
                if (o.getCategoryId() == cid) continue;
                int p = currentPages.get(o.getCategoryId());
                if (p > 1) other.append("page_").append(o.getCategoryId()).append('=').append(p).append('&');
            }

            blocks.add(new CategoryVideosDTO_24162121(c, counts.getOrDefault(cid, 0L), pr, shares, likes, other.toString()));
        }

        req.setAttribute("blocks", blocks);
        req.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(req, resp);
    }

    private int parsePage(String s) {
        try {
            int p = Integer.parseInt(s);
            return p < 1 ? 1 : p;
        } catch (NumberFormatException e) {
            return 1;
        }
    }
}
