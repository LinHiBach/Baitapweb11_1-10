package vn.hcmute.entity;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity(name = "Share")
@Table(name = "Shares")
public class Share_24110165 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ShareId")
    private int shareId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Username", referencedColumnName = "Username", nullable = false)
    private User_24110165 user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VideoId", referencedColumnName = "VideoId", nullable = false)
    private Video_24110165 video;

    @Column(name = "Emails", length = 255)
    private String emails;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SharedDate")
    private Date sharedDate;

    public Share_24110165() {
    }

    public Share_24110165(User_24110165 user, Video_24110165 video, String emails, Date sharedDate) {
        this.user = user;
        this.video = video;
        this.emails = emails;
        this.sharedDate = sharedDate;
    }

    public int getShareId() { return shareId; }
    public void setShareId(int shareId) { this.shareId = shareId; }
    public User_24110165 getUser() { return user; }
    public void setUser(User_24110165 user) { this.user = user; }
    public Video_24110165 getVideo() { return video; }
    public void setVideo(Video_24110165 video) { this.video = video; }
    public String getEmails() { return emails; }
    public void setEmails(String emails) { this.emails = emails; }
    public Date getSharedDate() { return sharedDate; }
    public void setSharedDate(Date sharedDate) { this.sharedDate = sharedDate; }
}
