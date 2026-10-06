package vn.hcmute.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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
@Table(name = "Orders")
@NamedQuery(name = "Order_24110165.findAll", query = "SELECT o FROM Order_24110165 o ORDER BY o.createdAt DESC")
public class Order_24110165 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderId")
    private int orderId;

    @Column(name = "Username", length = 50, nullable = false)
    private String username;

    @Column(name = "FullName", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String fullName;

    @Column(name = "Phone", length = 20, nullable = false)
    private String phone;

    @Column(name = "Address", columnDefinition = "NVARCHAR(500)", nullable = false)
    private String address;

    @Column(name = "Note", columnDefinition = "NVARCHAR(MAX)")
    private String note;

    @Column(name = "TotalPrice")
    private double totalPrice;

    @Column(name = "Status")
    private int status = 0; // 0: Chờ xác nhận, 1: Đang giao, 2: Hoàn tất, 3: Đã hủy

    @Column(name = "CreatedAt")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail_24110165> details = new ArrayList<>();

    public Order_24110165() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Order_24110165(String username, String fullName, String phone, String address, String note,
            double totalPrice) {
        this.username = username;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.note = note;
        this.totalPrice = totalPrice;
        this.status = 0;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderDetail_24110165> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail_24110165> details) {
        this.details = details;
    }

    public void addDetail(OrderDetail_24110165 detail) {
        this.details.add(detail);
        detail.setOrder(this);
    }

    public String getStatusText() {
        return vn.hcmute.utils.OrderStatus_24110165.getStatusText(this.status);
    }

    public String getStatusBadgeClass() {
        return vn.hcmute.utils.OrderStatus_24110165.getBadgeClass(this.status);
    }

    public String getStatusIcon() {
        return vn.hcmute.utils.OrderStatus_24110165.getIcon(this.status);
    }

    public String getStatusDescription() {
        return vn.hcmute.utils.OrderStatus_24110165.getDescription(this.status);
    }
}
