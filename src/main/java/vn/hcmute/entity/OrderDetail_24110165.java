package vn.hcmute.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "OrderDetails")
public class OrderDetail_24110165 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderId", referencedColumnName = "OrderId", nullable = false)
    private Order_24110165 order;

    @Column(name = "VideoId", length = 50, nullable = false)
    private String videoId;

    @Column(name = "VideoName", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String videoName;

    @Column(name = "Price", nullable = false)
    private double price;

    @Column(name = "Quantity", nullable = false)
    private int quantity;

    @Column(name = "Poster", columnDefinition = "NVARCHAR(500)")
    private String poster;

    public OrderDetail_24110165() {
    }

    public OrderDetail_24110165(Order_24110165 order, String videoId, String videoName, double price, int quantity) {
        this.order = order;
        this.videoId = videoId;
        this.videoName = videoName;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order_24110165 getOrder() {
        return order;
    }

    public void setOrder(Order_24110165 order) {
        this.order = order;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
