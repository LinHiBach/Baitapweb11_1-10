package vn.hcmute.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity(name = "Video")
@Table(name = "Videos")
@NamedQuery(name = "Video.findAll", query = "SELECT v FROM Video v")
public class Video_24110165 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "VideoId", length = 50)
    private String videoId;

    @Column(name = "Title", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String title;

    @Column(name = "Poster", columnDefinition = "NVARCHAR(500)")
    private String poster;

    @Column(name = "Views")
    private int views;

    @Column(name = "Description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(name = "Active")
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryId", referencedColumnName = "CategoryId")
    private Category_24110165 category;

    @OneToMany(mappedBy = "video", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Favorite_24110165> favorites = new ArrayList<>();

    @OneToMany(mappedBy = "video", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Share_24110165> shares = new ArrayList<>();

    @Column(name = "Price")
    private double price;

    public Video_24110165() {
        super();
    }

    public Video_24110165(String videoId, String title, String poster, int views, String description, boolean active,
            Category_24110165 category) {
        super();
        this.videoId = videoId;
        this.title = title;
        this.poster = poster;
        this.views = views;
        this.description = description;
        this.active = active;
        this.category = category;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Category_24110165 getCategory() {
        return category;
    }

    public void setCategory(Category_24110165 category) {
        this.category = category;
    }

    public List<Favorite_24110165> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite_24110165> favorites) {
        this.favorites = favorites;
    }

    public List<Share_24110165> getShares() {
        return shares;
    }

    public void setShares(List<Share_24110165> shares) {
        this.shares = shares;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Video_24110165 [videoId=" + videoId + ", title=" + title + ", poster=" + poster + ", views=" + views
                + ", description=" + description + ", active=" + active + ", price=" + price + "]";
    }
}
