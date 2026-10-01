package vn.iotstar.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Chi tiết đơn: lưu "chụp lại" tên + giá lúc mua, không khóa ngoại tới Videos (admin xóa video không ảnh hưởng đơn cũ). */
@Getter @Setter @NoArgsConstructor
@Entity(name = "OrderDetails")
@Table(name = "OrderDetails")
public class OrderDetails_24162121 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderDetailId")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "OrderId")
    private Orders_24162121 order;

    @Column(name = "VideoId", columnDefinition = "nvarchar(50)")
    private String videoId;

    @Column(name = "Title", columnDefinition = "nvarchar(200)")
    private String title;

    @Column(name = "Price")
    private long price;

    @Column(name = "Quantity")
    private int quantity;

    public long getLineTotal() {
        return price * quantity;
    }
}
