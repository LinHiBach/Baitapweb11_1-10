package vn.hcmute.controllers.admin;

import java.io.IOException;
import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import vn.hcmute.entity.Category_24110165;
import vn.hcmute.entity.User_24110165;
import vn.hcmute.entity.Video_24110165;
import vn.hcmute.services.ICategoryService_24110165;
import vn.hcmute.services.IVideoService_24110165;
import vn.hcmute.services.impl.CategoryServiceImpl_24110165;
import vn.hcmute.services.impl.VideoServiceImpl_24110165;
import vn.hcmute.utils.Constant_24110165;

@WebServlet(urlPatterns = {"/admin/videos", "/admin/videos/add", "/admin/videos/edit", "/admin/videos/delete"})
@MultipartConfig(fileSizeThreshold = 2 * 1024 * 1024, maxFileSize = 10 * 1024 * 1024, maxRequestSize = 50 * 1024 * 1024)
public class AdminVideoController_24110165 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int PAGE_SIZE = 6;
    private final IVideoService_24110165 videoService = new VideoServiceImpl_24110165();
    private final ICategoryService_24110165 categoryService = new CategoryServiceImpl_24110165();

    private boolean isAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        User_24110165 account = session == null ? null : (User_24110165) session.getAttribute("account");
        if (account == null || account.getRoleid() != 1) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return false;
        }
        return true;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req, resp)) return;
        String path = req.getServletPath();
        if (path.endsWith("/add")) {
            prepareForm(req);
            req.getRequestDispatcher("/views/admin/add-video.jsp").forward(req, resp);
        } else if (path.endsWith("/edit")) {
            Video_24110165 video = videoService.findById(req.getParameter("id"));
            if (video == null) { flash(req, "errorMsg", "Không tìm thấy video."); redirectList(req, resp); return; }
            req.setAttribute("video", video);
            prepareForm(req);
            req.getRequestDispatcher("/views/admin/edit-video.jsp").forward(req, resp);
        } else {
            int total = videoService.count();
            int totalPages = Math.max(1, (int) Math.ceil((double) total / PAGE_SIZE));
            int page = parsePage(req.getParameter("page"));
            page = Math.min(page, totalPages);
            req.setAttribute("videos", videoService.findAll(page, PAGE_SIZE));
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("totalVideos", total);
            req.getRequestDispatcher("/views/admin/list-video.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req, resp)) return;
        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();
        if (path.endsWith("/delete")) {
            String deleteId = value(req, "id");
            try {
                if (!deleteId.isEmpty() && videoService.findById(deleteId) != null) {
                    videoService.delete(deleteId);
                    flash(req, "successMsg", "Đã xóa video " + deleteId + ".");
                }
            } catch (Exception e) {
                flash(req, "errorMsg", "Không thể xóa video vì đang có dữ liệu liên quan.");
            }
            redirectList(req, resp);
            return;
        }
        String id = value(req, "videoId");
        String title = value(req, "title");
        String oldPoster = value(req, "oldPoster");
        String poster = handlePoster(req, oldPoster);
        String description = value(req, "description");
        int categoryId = parsePage(req.getParameter("categoryId"));
        Category_24110165 category = categoryService.findById(categoryId);
        if (id.isEmpty() || title.isEmpty() || category == null) {
            req.setAttribute("alert", "Mã video, tiêu đề và danh mục là bắt buộc.");
            prepareForm(req);
            req.setAttribute("video", new Video_24110165(id, title, poster, 0, description, "true".equals(req.getParameter("active")), category));
            req.getRequestDispatcher(path.endsWith("/add") ? "/views/admin/add-video.jsp" : "/views/admin/edit-video.jsp").forward(req, resp);
            return;
        }
        if (path.endsWith("/add")) {
            if (videoService.findById(id) != null) {
                req.setAttribute("alert", "Mã video đã tồn tại.");
                prepareForm(req);
                req.getRequestDispatcher("/views/admin/add-video.jsp").forward(req, resp); return;
            }
            videoService.insert(new Video_24110165(id, title, poster, 0, description, "true".equals(req.getParameter("active")), category));
            flash(req, "successMsg", "Thêm video thành công.");
        } else {
            Video_24110165 video = videoService.findById(id);
            if (video == null) { flash(req, "errorMsg", "Không tìm thấy video để cập nhật."); redirectList(req, resp); return; }
            video.setTitle(title); video.setPoster(poster); video.setDescription(description);
            video.setActive("true".equals(req.getParameter("active"))); video.setCategory(category);
            videoService.update(video);
            flash(req, "successMsg", "Cập nhật video thành công.");
        }
        redirectList(req, resp);
    }

    private static String value(HttpServletRequest req, String name) { String v = req.getParameter(name); return v == null ? "" : v.trim(); }
    private static int parsePage(String value) { try { return Math.max(1, Integer.parseInt(value)); } catch (Exception e) { return 1; } }
    private static void flash(HttpServletRequest req, String key, String message) { req.getSession().setAttribute(key, message); }
    private static void redirectList(HttpServletRequest req, HttpServletResponse resp) throws IOException { resp.sendRedirect(req.getContextPath() + "/admin/videos"); }

    private void prepareForm(HttpServletRequest req) {
        req.setAttribute("categories", categoryService.findAll());
        File directory = new File(Constant_24110165.UPLOAD_DIRECTORY);
        File[] files = directory.listFiles(file -> file.isFile() && file.getName().matches("(?i).+\\.(png|jpe?g|gif|webp)$"));
        if (files == null) {
            req.setAttribute("posterOptions", Collections.emptyList());
            return;
        }
        List<String> names = Arrays.stream(files).map(File::getName).sorted(String.CASE_INSENSITIVE_ORDER).toList();
        req.setAttribute("posterOptions", names);
    }

    private String handlePoster(HttpServletRequest req, String oldPoster) throws IOException, ServletException {
        Part part = req.getPart("posterFile");
        if (part != null && part.getSize() > 0) {
            String original = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            String extension = original.contains(".") ? original.substring(original.lastIndexOf('.')).toLowerCase() : "";
            if (!extension.matches("\\.(png|jpg|jpeg|gif|webp)")) {
                throw new ServletException("Poster phải là file ảnh PNG, JPG, GIF hoặc WEBP.");
            }
            File directory = new File(Constant_24110165.UPLOAD_DIRECTORY);
            if (!directory.exists() && !directory.mkdirs()) throw new IOException("Không thể tạo thư mục upload.");
            String fileName = System.currentTimeMillis() + extension;
            part.write(new File(directory, fileName).getAbsolutePath());
            return fileName;
        }
        String selected = value(req, "existingPoster");
        return selected.isEmpty() ? oldPoster : selected;
    }
}
