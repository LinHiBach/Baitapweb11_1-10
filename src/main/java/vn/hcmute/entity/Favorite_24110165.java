package vn.hcmute.entity;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

@Entity(name = "Favorite")
@Table(name = "Favorites")
public class Favorite_24110165 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FavoriteId")
    private int favoriteId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LikedDate")
    private Date likedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Username", referencedColumnName = "Username", nullable = false)
    private User_24110165 user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VideoId", referencedColumnName = "VideoId", nullable = false)
    private Video_24110165 video;

    public Favorite_24110165() {
    }

    public Favorite_24110165(Date likedDate, User_24110165 user, Video_24110165 video) {
        this.likedDate = likedDate;
        this.user = user;
        this.video = video;
    }

    public int getFavoriteId() { return favoriteId; }
    public void setFavoriteId(int favoriteId) { this.favoriteId = favoriteId; }
    public User_24110165 getUser() { return user; }
    public void setUser(User_24110165 user) { this.user = user; }
    public Video_24110165 getVideo() { return video; }
    public void setVideo(Video_24110165 video) { this.video = video; }
    public Date getLikedDate() { return likedDate; }
    public void setLikedDate(Date likedDate) { this.likedDate = likedDate; }
}
