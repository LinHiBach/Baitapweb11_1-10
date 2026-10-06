package vn.hcmute.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.hcmute.utils.Constant_24110165;

@WebServlet(urlPatterns = "/image")
public class ImageServlet_24110165 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String[] SEARCH_DIRS = {
        Constant_24110165.DIR,
        "C:\\upload",
        "C:\\Users\\LEGIO\\Documents\\workspace-spring-tools-for-eclipse-5.3.0.RELEASE\\DEMO\\upload",
        "upload"
    };
    private static final String[] DEFAULT_POSTERS = {
        "posterAVATAR.jpg", "GIAYBONGDA_VAPOR16.webp", "aobongda_nam_BDN.webp",
        "quabong_wc26.webp", "GIAYBONGDA17.webp", "cuo.webp"
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rawFileName = req.getParameter("fname");
        if (rawFileName == null || rawFileName.trim().isEmpty() || rawFileName.contains("..")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid file name");
            return;
        }

        // Hỗ trợ link ảnh trực tiếp từ internet
        if (rawFileName.startsWith("http://") || rawFileName.startsWith("https://")) {
            resp.sendRedirect(rawFileName);
            return;
        }

        String fileName = URLDecoder.decode(rawFileName.trim(), StandardCharsets.UTF_8);
        if (fileName.startsWith("/") || fileName.startsWith("\\")) {
            fileName = fileName.substring(1);
        }

        File targetFile = findImageFile(fileName, req);

        if (targetFile == null || !targetFile.exists() || !targetFile.isFile()) {
            // Trả về ảnh placeholder SVG nếu không tìm thấy file
            resp.setContentType("image/svg+xml");
            resp.setCharacterEncoding("UTF-8");
            String svg = "<svg xmlns='http://www.w3.org/2000/svg' width='300' height='200' viewBox='0 0 300 200'>"
                    + "<rect width='100%' height='100%' fill='#e2e8f0'/>"
                    + "<text x='50%' y='50%' font-family='Arial' font-size='16' fill='#64748b' text-anchor='middle' dominant-baseline='middle'>Product Image</text>"
                    + "</svg>";
            resp.getWriter().write(svg);
            return;
        }

        String mimeType = URLConnection.guessContentTypeFromName(targetFile.getName());
        if (mimeType == null) {
            String lowerName = targetFile.getName().toLowerCase();
            if (lowerName.endsWith(".png")) {
                mimeType = "image/png";
            } else if (lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg")) {
                mimeType = "image/jpeg";
            } else if (lowerName.endsWith(".gif")) {
                mimeType = "image/gif";
            } else if (lowerName.endsWith(".webp")) {
                mimeType = "image/webp";
            } else {
                mimeType = "application/octet-stream";
            }
        }

        resp.setContentType(mimeType);
        resp.setContentLengthLong(targetFile.length());
        resp.setHeader("Cache-Control", "public, max-age=86400");

        try (FileInputStream in = new FileInputStream(targetFile);
             OutputStream out = resp.getOutputStream()) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        }
    }

    private File findImageFile(String fileName, HttpServletRequest req) {
        for (String dirPath : SEARCH_DIRS) {
            if (dirPath != null && !dirPath.isEmpty()) {
                File file = new File(dirPath, fileName);
                if (file.exists() && file.isFile()) {
                    return file;
                }
            }
        }

        // Kiểm tra trong ServletContext thực tế
        try {
            String contextPath = req.getServletContext().getRealPath("/upload/" + fileName);
            if (contextPath != null) {
                File file = new File(contextPath);
                if (file.exists() && file.isFile()) {
                    return file;
                }
            }
        } catch (Exception ignored) {}

        String fallback = DEFAULT_POSTERS[Math.floorMod(fileName.hashCode(), DEFAULT_POSTERS.length)];
        for (String dirPath : SEARCH_DIRS) {
            File file = new File(dirPath, fallback);
            if (file.isFile()) return file;
        }
        return null;
    }
}
