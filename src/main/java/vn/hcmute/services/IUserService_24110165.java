package vn.hcmute.services;

import java.util.List;
import vn.hcmute.entity.User_24110165;

public interface IUserService_24110165 {
    User_24110165 login(String username, String password);
    User_24110165 get(String username);
    User_24110165 findByUsername(String username);
    User_24110165 findByEmail(String email);
    User_24110165 findByCodeAndEmail(String code, String email);
    List<User_24110165> findAll();
    void insert(User_24110165 user);
    void update(User_24110165 user);
    boolean register(String username, String password, String email, String fullname, String phone);
    boolean registerWithOtp(String username, String password, String email, String fullname, String phone, String otpCode);
    boolean checkExistEmail(String email);
    boolean checkExistUsername(String username);
    boolean checkExistPhone(String phone);
}
