package vn.hcmute.entity;

import java.io.Serializable;

public class CartItem_24110165 implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_MAX_QUANTITY = 10;

    private String videoId;
    private String title;
    private String poster;
    private double price;
    private int quantity;
    private int maxQuantity = DEFAULT_MAX_QUANTITY;

    public CartItem_24110165() {
    }

    public CartItem_24110165(String videoId, String title, String poster, double price, int quantity) {
        this.videoId = videoId;
        this.title = title;
        this.poster = poster;
        this.price = price;
        this.maxQuantity = DEFAULT_MAX_QUANTITY;
        setQuantitySafe(quantity);
    }

    public CartItem_24110165(String videoId, String title, String poster, double price, int quantity, int maxQuantity) {
        this.videoId = videoId;
        this.title = title;
        this.poster = poster;
        this.price = price;
        this.maxQuantity = maxQuantity > 0 ? maxQuantity : DEFAULT_MAX_QUANTITY;
        setQuantitySafe(quantity);
    }

    public double getTotalPrice() {
        return price * quantity;
    }

    public void increaseQty() {
        if (this.quantity < this.maxQuantity) {
            this.quantity++;
        }
    }

    public void decreaseQty() {
        if (this.quantity > 1) {
            this.quantity--;
        }
    }

    public void setQuantitySafe(int qty) {
        if (qty < 1) {
            this.quantity = 1;
        } else if (qty > this.maxQuantity) {
            this.quantity = this.maxQuantity;
        } else {
            this.quantity = qty;
        }
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
        setQuantitySafe(quantity);
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity > 0 ? maxQuantity : DEFAULT_MAX_QUANTITY;
        setQuantitySafe(this.quantity);
    }

    @Override
    public String toString() {
        return "CartItem_24110165 [videoId=" + videoId + ", title=" + title + ", price=" + price + ", quantity="
                + quantity + ", totalPrice=" + getTotalPrice() + "]";
    }
}
