package vn.hcmute.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Category")
@NamedQuery(name = "Category_24110165.findAll", query = "SELECT c FROM Category_24110165 c")
public class Category_24110165 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryId")
    private int categoryId;

    @Column(name = "Categoryname", columnDefinition = "NVARCHAR(100)")
    private String categoryname;

    @Column(name = "Categorycode", columnDefinition = "NVARCHAR(100)")
    private String categorycode;

    @Column(name = "Images", columnDefinition = "NVARCHAR(500)")
    private String images;

    @Column(name = "Status")
    private Boolean status = true;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Video_24110165> videos = new ArrayList<>();

    public Category_24110165() {
        super();
    }

    public Category_24110165(int categoryId, String categoryname, String images, Integer status) {
        super();
        this.categoryId = categoryId;
        this.categoryname = categoryname;
        this.images = images;
        this.status = status == null || status == 1;
    }

    public Category_24110165(String categoryname, String images, Integer status) {
        super();
        this.categoryname = categoryname;
        this.images = images;
        this.status = status == null || status == 1;
    }

    public Category_24110165(String categoryname, String images) {
        super();
        this.categoryname = categoryname;
        this.images = images;
        this.status = true;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getStatus() {
        return Boolean.TRUE.equals(status) ? 1 : 0;
    }

    public void setStatus(int status) {
        this.status = status == 1;
    }

    public List<Video_24110165> getVideos() {
        return videos;
    }

    public void setVideos(List<Video_24110165> videos) {
        this.videos = videos;
    }

    public Video_24110165 addVideo(Video_24110165 video) {
        getVideos().add(video);
        video.setCategory(this);
        return video;
    }

    public Video_24110165 removeVideo(Video_24110165 video) {
        getVideos().remove(video);
        video.setCategory(null);
        return video;
    }

    // ==========================================
    // CÁC HÀM ALIAS GETTER/SETTER TƯƠNG THÍCH JSP EL
    // ==========================================
    public int getId() {
        return categoryId;
    }

    public void setId(int id) {
        this.categoryId = id;
    }

    public int getCategoryid() {
        return categoryId;
    }

    public void setCategoryid(int categoryid) {
        this.categoryId = categoryid;
    }

    public String getName() {
        return categoryname;
    }

    public void setName(String name) {
        this.categoryname = name;
    }

    public String getCategoryName() {
        return categoryname;
    }

    public void setCategoryName(String categoryName) {
        this.categoryname = categoryName;
    }

    public String getIcon() {
        return images;
    }

    public void setIcon(String icon) {
        this.images = icon;
    }

    public String getIcons() {
        return images;
    }

    public void setIcons(String icons) {
        this.images = icons;
    }

    public String getCategorycode() {
        return categorycode;
    }

    public void setCategorycode(String categorycode) {
        this.categorycode = categorycode;
    }

    @Override
    public String toString() {
        return "Category_24110165 [categoryId=" + categoryId + ", categoryname=" + categoryname + ", images=" + images
                + ", status=" + status + "]";
    }
}
