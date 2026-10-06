package vn.hcmute.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class OrderStatus_24110165 {
    public static final int NEW = 0;         // Đơn hàng mới
    public static final int CONFIRMED = 1;   // Đã xác nhận
    public static final int PREPARING = 2;   // Chuẩn bị hàng
    public static final int SHIPPING = 3;    // Vận chuyển
    public static final int DELIVERING = 4;  // Giao hàng
    public static final int DELIVERED = 5;   // Đã giao
    public static final int CANCELLED = 6;   // Đơn hàng hủy
    public static final int RETURNED = 7;    // Đơn hàng hoàn

    public static final int[] ALL_STATUSES = {
        NEW, CONFIRMED, PREPARING, SHIPPING, DELIVERING, DELIVERED, CANCELLED, RETURNED
    };

    public static String getStatusText(int status) {
        switch (status) {
            case NEW:
                return "Đơn hàng mới";
            case CONFIRMED:
                return "Đã xác nhận";
            case PREPARING:
                return "Chuẩn bị hàng";
            case SHIPPING:
                return "Vận chuyển";
            case DELIVERING:
                return "Giao hàng";
            case DELIVERED:
                return "Đã giao";
            case CANCELLED:
                return "Đơn hàng hủy";
            case RETURNED:
                return "Đơn hàng hoàn";
            default:
                return "Không xác định (" + status + ")";
        }
    }

    public static String getBadgeClass(int status) {
        switch (status) {
            case NEW:
                return "badge bg-info text-dark";
            case CONFIRMED:
                return "badge bg-primary text-white";
            case PREPARING:
                return "badge bg-warning text-dark";
            case SHIPPING:
                return "badge bg-secondary text-white";
            case DELIVERING:
                return "badge bg-indigo text-white";
            case DELIVERED:
                return "badge bg-success text-white";
            case CANCELLED:
                return "badge bg-danger text-white";
            case RETURNED:
                return "badge bg-dark text-white";
            default:
                return "badge bg-light text-dark border";
        }
    }

    public static String getIcon(int status) {
        switch (status) {
            case NEW:
                return "bi-clock-history";
            case CONFIRMED:
                return "bi-check2-circle";
            case PREPARING:
                return "bi-box-seam";
            case SHIPPING:
                return "bi-truck";
            case DELIVERING:
                return "bi-bicycle";
            case DELIVERED:
                return "bi-check-all";
            case CANCELLED:
                return "bi-x-circle";
            case RETURNED:
                return "bi-arrow-counterclockwise";
            default:
                return "bi-question-circle";
        }
    }

    public static String getDescription(int status) {
        switch (status) {
            case NEW:
                return "Đơn hàng vừa được đặt và đang chờ tiếp nhận.";
            case CONFIRMED:
                return "Đơn hàng đã được xác nhận thành công bởi cửa hàng.";
            case PREPARING:
                return "Cửa hàng đang đóng gói và chuẩn bị xuất kho.";
            case SHIPPING:
                return "Đơn hàng đã xuất kho và đang trong quá trình luân chuyển.";
            case DELIVERING:
                return "Shipper đang trên đường giao kiện hàng tới địa chỉ nhận.";
            case DELIVERED:
                return "Đơn hàng đã giao thành công và hoàn tất thanh toán COD.";
            case CANCELLED:
                return "Đơn hàng đã bị hủy.";
            case RETURNED:
                return "Đơn hàng đã được hoàn trả về cửa hàng.";
            default:
                return "";
        }
    }

    public static Map<Integer, String> getAllStatusMap() {
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(NEW, "Đơn hàng mới");
        map.put(CONFIRMED, "Đã xác nhận");
        map.put(PREPARING, "Chuẩn bị hàng");
        map.put(SHIPPING, "Vận chuyển");
        map.put(DELIVERING, "Giao hàng");
        map.put(DELIVERED, "Đã giao");
        map.put(CANCELLED, "Đơn hàng hủy");
        map.put(RETURNED, "Đơn hàng hoàn");
        return map;
    }
}
