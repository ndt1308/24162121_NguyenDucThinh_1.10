package vn.iotstar.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity(name = "Orders")
@Table(name = "Orders")
public class Orders_24162121 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderId")
    private Long orderId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "Username")
    private Users_24162121 user;

    @Column(name = "OrderDate", nullable = false)
    private LocalDateTime orderDate;

    /** Lưu dạng chữ: NEW, CONFIRMED, PREPARING, SHIPPING, DELIVERING, DELIVERED, CANCELLED, RETURNED */
    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 20)
    private OrderStatus_24162121 status = OrderStatus_24162121.NEW;

    @Column(name = "ReceiverName", columnDefinition = "nvarchar(100)")
    private String receiverName;

    @Column(name = "Phone", columnDefinition = "nvarchar(15)")
    private String phone;

    @Column(name = "Address", columnDefinition = "nvarchar(300)")
    private String address;

    @Column(name = "Note", columnDefinition = "nvarchar(500)")
    private String note;

    @Column(name = "PaymentMethod", columnDefinition = "nvarchar(20)")
    private String paymentMethod = "COD";

    @Column(name = "TotalAmount")
    private long totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetails_24162121> details = new ArrayList<>();

    public String getOrderDateText() {
        return orderDate == null ? "" : orderDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
