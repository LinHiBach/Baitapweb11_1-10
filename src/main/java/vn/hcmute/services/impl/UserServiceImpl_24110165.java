package vn.hcmute.services.impl;

import java.sql.Date;
import java.util.List;
import vn.hcmute.dao.IUserDao_24110165;
import vn.hcmute.dao.impl.UserDaoImpl_24110165;
import vn.hcmute.entity.User_24110165;
import vn.hcmute.services.IUserService_24110165;
import vn.hcmute.utils.EmailUtils_24110165;
import vn.hcmute.utils.PasswordUtils_24110165;

public class UserServiceImpl_24110165 implements IUserService_24110165 {
    private final IUserDao_24110165 userDao = new UserDaoImpl_24110165();

    @Override
    public User_24110165 login(String username, String password) {
        User_24110165 user = this.get(username);
        if (user != null && password != null) {
            // Kiểm tra mật khẩu mã hóa BCrypt hoặc plain text
            if (PasswordUtils_24110165.checkPassword(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User_24110165 get(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User_24110165 findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User_24110165 findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User_24110165 findByCodeAndEmail(String code, String email) {
        return userDao.findByCodeAndEmail(code, email);
    }

    @Override
    public List<User_24110165> findAll() {
        return userDao.findAll();
    }

    @Override
    public boolean register(String username, String password, String email, String fullname, String phone) {
        String otpCode = EmailUtils_24110165.generateOtp();
        return registerWithOtp(username, password, email, fullname, phone, otpCode);
    }

    @Override
    public boolean registerWithOtp(String username, String password, String email, String fullname, String phone, String otpCode) {
        if (userDao.checkExistUsername(username) || userDao.checkExistEmail(email)) {
            return false;
        }
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);

        // Mã hóa mật khẩu bằng BCrypt
        String hashedPassword = PasswordUtils_24110165.hashPassword(password);

        // Tạo user mới với status = 0 (chưa kích hoạt) và gán mã OTP
        User_24110165 newUser = new User_24110165(email, username, fullname, hashedPassword, null, 3, phone, date);
        newUser.setCode(otpCode);
        newUser.setStatus(0); // Chờ xác thực OTP

        userDao.insert(newUser);

        // Gửi email chứa mã OTP
        EmailUtils_24110165.sendOtpEmail(email, otpCode, "register");

        return true;
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userDao.checkExistEmail(email);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userDao.checkExistUsername(username);
    }

    @Override
    public boolean checkExistPhone(String phone) {
        return userDao.checkExistPhone(phone);
    }

    @Override
    public void insert(User_24110165 user) {
        userDao.insert(user);
    }

    @Override
    public void update(User_24110165 user) {
        userDao.update(user);
    }
}
