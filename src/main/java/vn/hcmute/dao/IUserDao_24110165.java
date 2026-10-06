package vn.hcmute.dao;

import java.util.List;
import vn.hcmute.entity.User_24110165;

public interface IUserDao_24110165 {
    User_24110165 findByUsername(String username);
    User_24110165 findByEmail(String email);
    User_24110165 findByCodeAndEmail(String code, String email);
    List<User_24110165> findAll();
    void insert(User_24110165 user);
    void update(User_24110165 user);

    boolean checkExistEmail(String email);
    boolean checkExistUsername(String username);
    boolean checkExistPhone(String phone);

    User_24110165 get(String username);
}
