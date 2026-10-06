package vn.hcmute.utils;

import java.util.Properties;
import java.util.Random;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailUtils_24110165 {

    // Thông tin Gmail thật của bạn
    private static final String FROM_EMAIL = "huybach219@gmail.com";
    private static final String APP_PASSWORD = "ukce gyrp zyfz fnul"; 
    public static String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public static boolean sendOtpEmail(String toEmail, String otpCode, String type) {
        String subject = "Xác nhận mã OTP - VideoHub";
        String actionText = "KÍCH HOẠT TÀI KHOẢN";
        if ("reset".equalsIgnoreCase(type)) {
            subject = "Xác nhận đặt lại mật khẩu - VideoHub";
            actionText = "ĐẶT LẠI MẬT KHẨU";
        }

        System.out.println("\n=======================================================");
        System.out.println("🔥 [MÃ OTP XÁC THỰC]: " + otpCode);
        System.out.println("📧 Gửi tới: " + toEmail + " | Mục đích: " + actionText);
        System.out.println("=======================================================\n");

        String htmlContent = "<div style=\"font-family: Arial, sans-serif; max-width: 500px; margin: 0 auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 10px;\">"
                + "<h2 style=\"color: #2563eb; text-align: center;\">VideoHub</h2>"
                + "<p>Xin chào,</p>"
                + "<p>Mã OTP của bạn để <strong>" + actionText + "</strong> là:</p>"
                + "<div style=\"background: #f0f7ff; color: #0077ff; font-size: 28px; font-weight: bold; text-align: center; padding: 15px; border-radius: 8px; letter-spacing: 5px; margin: 20px 0;\">"
                + otpCode + "</div>"
                + "<p style=\"color: #666; font-size: 13px;\">Vui lòng nhập mã này vào trang web để tiếp tục. Mã OTP có hiệu lực trong thời gian ngắn.</p>"
                + "<hr style=\"border: none; border-top: 1px solid #eee; margin: 20px 0;\">"
                + "<p style=\"color: #999; font-size: 12px; text-align: center;\">Đây là email tự động từ hệ thống VideoHub, vui lòng không phản hồi.</p>"
                + "</div>";

        final String mailSubject = subject;
        final String mailHtml = htmlContent;

        // Gửi email bất đồng bộ qua luồng ngầm để giao diện web phản hồi tức thì
        new Thread(() -> {
            sendHtmlEmail(toEmail, mailSubject, mailHtml);
        }).start();

        return true;
    }

    public static boolean sendOtpEmail(String toEmail, String otpCode) {
        return sendOtpEmail(toEmail, otpCode, "register");
    }

    public static boolean sendHtmlEmail(String toEmail, String subject, String htmlBody) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.connectiontimeout", "8000");
        props.put("mail.smtp.timeout", "8000");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        };

        try {
            Session session = Session.getInstance(props, auth);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, "VideoHub"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject, "UTF-8");
            message.setContent(htmlBody, "text/html; charset=UTF-8");

            Transport.send(message);
            System.out.println("✅ [EmailUtils_24110165] Đã gửi email thực tế thành công tới: " + toEmail);
            return true;
        } catch (Exception e) {
            System.err.println("⚠️ [EmailUtils_24110165] Lỗi gửi Gmail SMTP: " + e.getMessage());
            return false;
        }
    }
}
