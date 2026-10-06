package vn.hcmute.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils_24110165 {

    /**
     * Băm mật khẩu sử dụng thuật toán BCrypt
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            return plainPassword;
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    /**
     * Kiểm tra mật khẩu plain-text với mật khẩu đã băm (BCrypt)
     * Hoặc tương thích ngược với plain-text cũ
     */
    public static boolean checkPassword(String plainPassword, String storedPassword) {
        if (plainPassword == null || storedPassword == null) {
            return false;
        }

        // Tương thích ngược nếu trong DB đang lưu chuỗi chưa hash (VD: '123')
        if (plainPassword.equals(storedPassword)) {
            return true;
        }

        try {
            if (storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$") || storedPassword.startsWith("$2y$")) {
                return BCrypt.checkpw(plainPassword, storedPassword);
            }
        } catch (Exception ignored) {}

        return false;
    }
}
