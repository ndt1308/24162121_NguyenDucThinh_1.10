package vn.iotstar.entity;

public enum OrderStatus_24162121 {
    NEW("Đơn hàng mới", "bg-primary"),
    CONFIRMED("Đã xác nhận", "bg-info text-dark"),
    PREPARING("Chuẩn bị hàng", "bg-warning text-dark"),
    SHIPPING("Vận chuyển", "bg-secondary"),
    DELIVERING("Giao hàng", "bg-dark"),
    DELIVERED("Đã giao", "bg-success"),
    CANCELLED("Đơn hàng hủy", "bg-danger"),
    RETURNED("Đơn hàng hoàn", "bg-light text-dark border");

    private final String label;
    private final String badge;

    OrderStatus_24162121(String label, String badge) {
        this.label = label;
        this.badge = badge;
    }

    public String getLabel() { return label; }
    public String getBadge() { return badge; }
    public String getCode() { return name(); }

    /** Đổi chuỗi trên URL (?status=NEW) thành enum; sai/rỗng -> null (= tất cả). */
    public static OrderStatus_24162121 fromCode(String code) {
        if (code == null) return null;
        for (OrderStatus_24162121 s : values()) {
            if (s.name().equalsIgnoreCase(code.trim())) return s;
        }
        return null;
    }
}
