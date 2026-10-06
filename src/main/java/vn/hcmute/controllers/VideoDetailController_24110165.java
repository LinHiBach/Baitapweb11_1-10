package vn.hcmute.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.hcmute.entity.Video_24110165;
import vn.hcmute.services.IVideoService_24110165;
import vn.hcmute.services.impl.VideoServiceImpl_24110165;

@WebServlet("/video/detail")
public class VideoDetailController_24110165 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IVideoService_24110165 videoService = new VideoServiceImpl_24110165();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Video_24110165 video = id == null ? null : videoService.findById(id);
        if (video == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy video");
            return;
        }
        video.setViews(video.getViews() + 1);
        videoService.update(video);
        req.setAttribute("video", video);
        req.setAttribute("likeCount", videoService.countFavorites(id));
        req.setAttribute("shareCount", videoService.countShares(id));
        req.getRequestDispatcher("/views/web/video-detail.jsp").forward(req, resp);
    }
}
