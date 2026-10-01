package vn.iotstar.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity(name = "CartItems")
@Table(name = "CartItems",
       uniqueConstraints = @UniqueConstraint(columnNames = {"Username", "VideoId"}))
public class CartItems_24162121 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartItemId")
    private int cartItemId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "Username")
    private Users_24162121 user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "VideoId")
    private Videos_24162121 video;

    @Column(name = "Quantity", nullable = false)
    private int quantity;

    /** Thành tiền = đơn giá x số lượng. */
    public long getLineTotal() {
        return video.getPrice() * quantity;
    }
}
