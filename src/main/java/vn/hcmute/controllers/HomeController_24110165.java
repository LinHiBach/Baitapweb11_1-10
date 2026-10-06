package vn.hcmute.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.hcmute.entity.Category_24110165;
import vn.hcmute.entity.Video_24110165;
import vn.hcmute.services.ICategoryService_24110165;
import vn.hcmute.services.IVideoService_24110165;
import vn.hcmute.services.impl.CategoryServiceImpl_24110165;
import vn.hcmute.services.impl.VideoServiceImpl_24110165;

@WebServlet("/home")
public class HomeController_24110165 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int PAGE_SIZE = 3;
    private final ICategoryService_24110165 categoryService = new CategoryServiceImpl_24110165();
    private final IVideoService_24110165 videoService = new VideoServiceImpl_24110165();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category_24110165> categories = categoryService.findAll();
        Map<Integer, List<Video_24110165>> videosByCategory = new HashMap<>();
        Map<Integer, Integer> counts = new HashMap<>();
        Map<Integer, Integer> currentPages = new HashMap<>();
        Map<Integer, Integer> totalPages = new HashMap<>();
        Map<String, Long> likeCounts = new HashMap<>();
        Map<String, Long> shareCounts = new HashMap<>();

        for (Category_24110165 category : categories) {
            int id = category.getCategoryId();
            int count = videoService.countByCategoryId(id);
            int pages = Math.max(1, (int) Math.ceil((double) count / PAGE_SIZE));
            int page = page(req.getParameter("page_" + id));
            page = Math.min(page, pages);
            counts.put(id, count);
            currentPages.put(id, page);
            totalPages.put(id, pages);
            List<Video_24110165> videos = videoService.findByCategoryId(id, page, PAGE_SIZE);
            videosByCategory.put(id, videos);
            for (Video_24110165 video : videos) {
                likeCounts.put(video.getVideoId(), videoService.countFavorites(video.getVideoId()));
                shareCounts.put(video.getVideoId(), videoService.countShares(video.getVideoId()));
            }
        }

        req.setAttribute("categories", categories);
        req.setAttribute("videosByCategory", videosByCategory);
        req.setAttribute("videoCounts", counts);
        req.setAttribute("currentPages", currentPages);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("likeCounts", likeCounts);
        req.setAttribute("shareCounts", shareCounts);
        req.getRequestDispatcher("/views/web/home.jsp").forward(req, resp);
    }

    private static int page(String value) {
        try { return Math.max(1, Integer.parseInt(value)); }
        catch (Exception ignored) { return 1; }
    }
}
