package vn.hcmute.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import jakarta.servlet.annotation.WebFilter;

@WebFilter(filterName = "sitemesh", urlPatterns = "/*")
public class SiteMeshFilter_24110165 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}


    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder
            // Gán decorator layout cho các phân hệ
            .addDecoratorPath("/admin/*", "/views/decorators/admin.jsp")
            .addDecoratorPath("/user/*", "/views/decorators/web.jsp")
            .addDecoratorPath("/home*", "/views/decorators/web.jsp")
            .addDecoratorPath("/video/*", "/views/decorators/web.jsp")
            // Loại trừ các trang không cần bọc layout (Login, Register, OTP, API, Image Stream)
            .addExcludedPath("/login*")
            .addExcludedPath("/register*")
            .addExcludedPath("/verify-otp*")
            .addExcludedPath("/image*")
            .addExcludedPath("/static/*");
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.substring(contextPath.length());

        // Skip static resources, images, assets, and uploads
        if (path.startsWith("/image") || path.startsWith("/assets") || path.startsWith("/static") || path.startsWith("/upload")
                || path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".png") || path.endsWith(".jpg")
                || path.endsWith(".jpeg") || path.endsWith(".gif") || path.endsWith(".ico") || path.endsWith(".woff2")) {
            chain.doFilter(request, response);
            return;
        }

        // Avoid re-decorating decorator JSPs directly
        if (path.startsWith("/WEB-INF/decorators")) {
            chain.doFilter(request, response);
            return;
        }

        String decoratorPath = getDecoratorPath(path);

        // If no decorator is mapped for this URL (e.g. /login, /register, /, /waiting), pass directly through filter chain
        if (decoratorPath == null) {
            chain.doFilter(request, response);
            return;
        }

        FilteredRequestWrapper wrappedRequest = new FilteredRequestWrapper(req);
        CharResponseWrapper responseWrapper = new CharResponseWrapper(resp);

        chain.doFilter(wrappedRequest, responseWrapper);

        // If response was redirected or errored, stop
        if (responseWrapper.isRedirectedOrError() || resp.isCommitted()) {
            return;
        }

        String contentType = responseWrapper.getContentType();
        String capturedText = responseWrapper.toString();

        if (capturedText != null && !capturedText.trim().isEmpty() 
                && (contentType == null || contentType.contains("text/html"))) {

            // Extract title, head, and body from target page
            String title = extractPattern(capturedText, "<title>(.*?)</title>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
            String head = extractPattern(capturedText, "<head>(.*?)</head>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
            String rawBody = extractPattern(capturedText, "<body[^>]*>(.*?)</body>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

            String body = (rawBody != null && !rawBody.trim().isEmpty()) ? rawBody : capturedText;

            // Extract inner main content for admin pages to avoid duplicating topbar/sidebar
            if (path.startsWith("/admin")) {
                int mcIdx = body.indexOf("class=\"main-content\"");
                if (mcIdx == -1) mcIdx = body.indexOf("class='main-content'");
                if (mcIdx != -1) {
                    int contentStart = body.indexOf(">", mcIdx);
                    if (contentStart != -1) {
                        String inner = body.substring(contentStart + 1);
                        int scriptIdx = inner.lastIndexOf("<script");
                        if (scriptIdx != -1) {
                            inner = inner.substring(0, scriptIdx);
                        }
                        int lastDiv = inner.lastIndexOf("</div>");
                        if (lastDiv != -1) {
                            inner = inner.substring(0, lastDiv);
                        }
                        if (inner != null && !inner.trim().isEmpty()) {
                            body = inner.trim();
                        }
                    }
                }
            } else if (path.startsWith("/home")) {
                int topbarEnd = body.indexOf("class=\"topbar-user\"");
                if (topbarEnd != -1) {
                    int containerIdx = body.indexOf("<div class=\"container", topbarEnd);
                    if (containerIdx != -1) {
                        String inner = body.substring(containerIdx);
                        int scriptIdx = inner.lastIndexOf("<script");
                        if (scriptIdx != -1) {
                            inner = inner.substring(0, scriptIdx);
                        }
                        if (inner != null && !inner.trim().isEmpty()) {
                            body = inner.trim();
                        }
                    }
                }
            }

            if (head != null && title != null) {
                head = head.replaceAll("(?i)<title>.*?</title>", "");
            }

            req.setAttribute("title", title != null ? title : "Shopping Service");
            req.setAttribute("head", head != null ? head : "");
            req.setAttribute("body", body);

            req.setAttribute("sitemesh.title", title != null ? title : "Shopping Service");
            req.setAttribute("sitemesh.head", head != null ? head : "");
            req.setAttribute("sitemesh.body", body);

            // Include decorator JSP to render decorator template HTML
            CharResponseWrapper decoratorWrapper = new CharResponseWrapper(resp);
            try {
                req.getRequestDispatcher(decoratorPath).include(req, decoratorWrapper);
                String decoratedContent = decoratorWrapper.toString();

                if (decoratedContent != null && !decoratedContent.trim().isEmpty()) {
                    // Fallback string replacement for custom sitemesh tags
                    decoratedContent = decoratedContent
                            .replace("<sitemesh:write property=\"body\"/>", body)
                            .replace("<sitemesh:write property='body'/>", body)
                            .replace("<decorator:body/>", body)
                            .replace("${sitemesh.body}", body)
                            .replace("<sitemesh:write property=\"title\"/>", title != null ? title : "")
                            .replace("<sitemesh:write property='title'/>", title != null ? title : "")
                            .replace("<decorator:title/>", title != null ? title : "")
                            .replace("<sitemesh:write property=\"head\"/>", head != null ? head : "")
                            .replace("<sitemesh:write property='head'/>", head != null ? head : "")
                            .replace("<decorator:head/>", head != null ? head : "");

                    resp.setContentType("text/html;charset=UTF-8");
                    PrintWriter out = resp.getWriter();
                    out.write(decoratedContent);
                    out.flush();
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Fallback: Write captured text directly using Writer
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.write(capturedText != null ? capturedText : "");
        out.flush();
    }

    private String getDecoratorPath(String path) {
        if (path == null || path.equals("/") || path.trim().isEmpty()) {
            return null;
        }
        if (path.startsWith("/admin")) {
            return "/WEB-INF/decorators/admin.jsp";
        } else if (path.startsWith("/login") || path.startsWith("/register") || path.startsWith("/waiting") 
                || path.startsWith("/logout") || path.startsWith("/verify-otp")) {
            return null;
        } else {
            return "/WEB-INF/decorators/web.jsp";
        }
    }

    private String extractPattern(String content, String regex, int flags) {
        Pattern pattern = Pattern.compile(regex, flags);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }

    @Override
    public void destroy() {}

    private static class FilteredRequestWrapper extends HttpServletRequestWrapper {
        public FilteredRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public RequestDispatcher getRequestDispatcher(String path) {
            RequestDispatcher dispatcher = super.getRequestDispatcher(path);
            if (dispatcher == null) {
                return null;
            }
            return new RequestDispatcherWrapper(dispatcher);
        }
    }

    private static class RequestDispatcherWrapper implements RequestDispatcher {
        private final RequestDispatcher targetDispatcher;

        public RequestDispatcherWrapper(RequestDispatcher targetDispatcher) {
            this.targetDispatcher = targetDispatcher;
        }

        @Override
        public void forward(ServletRequest request, ServletResponse response) throws ServletException, IOException {
            targetDispatcher.include(request, response);
        }

        @Override
        public void include(ServletRequest request, ServletResponse response) throws ServletException, IOException {
            targetDispatcher.include(request, response);
        }
    }

    private static class CharResponseWrapper extends HttpServletResponseWrapper {
        private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        private PrintWriter writer;
        private ServletOutputStream servletOutputStream;
        private boolean redirectedOrError = false;

        public CharResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public void sendRedirect(String location) throws IOException {
            this.redirectedOrError = true;
            super.sendRedirect(location);
        }

        @Override
        public void sendError(int sc) throws IOException {
            this.redirectedOrError = true;
            super.sendError(sc);
        }

        @Override
        public void sendError(int sc, String msg) throws IOException {
            this.redirectedOrError = true;
            super.sendError(sc);
        }

        @Override
        public void flushBuffer() throws IOException {
            if (writer != null) {
                writer.flush();
            }
        }

        public boolean isRedirectedOrError() {
            return redirectedOrError;
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            if (writer != null) {
                throw new IllegalStateException("getWriter() has already been called for this response");
            }
            if (servletOutputStream == null) {
                servletOutputStream = new ServletOutputStream() {
                    @Override
                    public boolean isReady() { return true; }
                    @Override
                    public void setWriteListener(WriteListener writeListener) {}
                    @Override
                    public void write(int b) { outputStream.write(b); }
                    @Override
                    public void write(byte[] b, int off, int len) { outputStream.write(b, off, len); }
                };
            }
            return servletOutputStream;
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            if (servletOutputStream != null) {
                throw new IllegalStateException("getOutputStream() has already been called for this response");
            }
            if (writer == null) {
                String encoding = getCharacterEncoding();
                if (encoding == null) encoding = "UTF-8";
                writer = new PrintWriter(new OutputStreamWriter(outputStream, encoding), true);
            }
            return writer;
        }

        @Override
        public String toString() {
            if (writer != null) {
                writer.flush();
            }
            String encoding = getCharacterEncoding();
            if (encoding == null) encoding = "UTF-8";
            try {
                return outputStream.toString(encoding);
            } catch (Exception e) {
                return outputStream.toString();
            }
        }
    }
}
