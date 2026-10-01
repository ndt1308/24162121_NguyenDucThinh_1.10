package vn.iotstar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity(name = "Videos")
@Table(name = "Videos")
public class Videos_24162121 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "VideoId", columnDefinition = "nvarchar(50)")
    private String videoId;

    @Column(name = "Title", columnDefinition = "nvarchar(200)")
    private String title;

    @Column(name = "Poster", columnDefinition = "nvarchar(50)")
    private String poster;

    @Column(name = "Views")
    private Integer views;

    @Column(name = "Description", columnDefinition = "nvarchar(500)")
    private String description;

    @Column(name = "Active")
    private Boolean active;

    @Column(name = "Price")
    private Long price;      // giá (VND)

    @Column(name = "Stock")
    private Integer stock;   // tồn kho

    // Lombok bỏ qua getter tự viết -> luôn trả số, không bao giờ null
    public Long getPrice() { return price == null ? 0L : price; }
    public Integer getStock() { return stock == null ? 0 : stock; }

    @ManyToOne
    @JoinColumn(name = "CategoryId")
    private Category_24162121 category;

    @OneToMany(mappedBy = "video")
    private List<Shares_24162121> shares;

    @OneToMany(mappedBy = "video")
    private List<Favorites_24162121> favorites;
}
