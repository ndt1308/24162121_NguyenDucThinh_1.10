package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import vn.iotstar.entity.Videos_24162121;
import vn.iotstar.service.IVideoService_24162121;
import vn.iotstar.service.impl.VideoServiceImpl_24162121;

/** Câu 3: trang chi tiết 01 video  ->  /video?id=VD0001 */
@WebServlet("/video")
public class VideoDetailController_24162121 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IVideoService_24162121 videoService = new VideoServiceImpl_24162121();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        Videos_24162121 video = videoService.findById(id);
        if (video == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy video");
            return;
        }
        req.setAttribute("video", video);
        req.setAttribute("shareCount", videoService.countShares(id));   // Share(n)
        req.setAttribute("likeCount", videoService.countLikes(id));     // Like(n)
        req.getRequestDispatcher("/WEB-INF/views/video-detail.jsp").forward(req, resp);
    }
}
