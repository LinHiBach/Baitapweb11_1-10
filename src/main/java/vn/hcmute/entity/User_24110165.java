package vn.hcmute.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "User")
@Table(name = "Users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User_24110165 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Username", length = 50)
    private String username;

    @Column(name = "Password", length = 255, nullable = false)
    private String password;

    @Column(name = "Phone", length = 20)
    private String phone;

    @Column(name = "Fullname", columnDefinition = "NVARCHAR(255)")
    private String fullname;

    @Column(name = "Email", length = 255, unique = true)
    private String email;

    @Column(name = "Admin")
    private boolean admin;

    @Column(name = "Active")
    private boolean active = true;

    @Column(name = "Images", columnDefinition = "NVARCHAR(500)")
    private String images;

    @Column(name = "Code", length = 10)
    private String code;

    @OneToMany(mappedBy = "user")
    private List<Favorite_24110165> favorites = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Share_24110165> shares = new ArrayList<>();

    public User_24110165() {}

    public User_24110165(String email, String username, String fullname, String password, String images,
                int roleid, String phone, Date ignoredCreatedDate) {
        this.email = email;
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.images = images;
        this.admin = roleid == 1;
        this.phone = phone;
        this.active = true;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    public String getFullName() { return fullname; }
    public void setFullName(String fullname) { this.fullname = fullname; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean admin) { this.admin = admin; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }
    public String getAvatar() { return images; }
    public void setAvatar(String avatar) { this.images = avatar; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public List<Favorite_24110165> getFavorites() { return favorites; }
    public void setFavorites(List<Favorite_24110165> favorites) { this.favorites = favorites; }
    public List<Share_24110165> getShares() { return shares; }
    public void setShares(List<Share_24110165> shares) { this.shares = shares; }

    @Transient
    public int getRoleid() { return admin ? 1 : 3; }
    public void setRoleid(int roleid) { this.admin = roleid == 1; }
    @Transient
    public int getStatus() { return active ? 1 : 0; }
    public void setStatus(int status) { this.active = status == 1; }
}
