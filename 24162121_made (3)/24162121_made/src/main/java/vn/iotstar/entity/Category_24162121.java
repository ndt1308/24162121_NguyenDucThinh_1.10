package vn.iotstar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity(name = "Category")
@Table(name = "Category")
public class Category_24162121 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryId")
    private int categoryId;

    @Column(name = "Categoryname", columnDefinition = "nvarchar(100)")
    private String categoryname;

    @Column(name = "Categorycode", columnDefinition = "nvarchar(100)")
    private String categorycode;

    @Column(name = "Images", columnDefinition = "nvarchar(500)")
    private String images;

    @Column(name = "Status")
    private Boolean status;

    @OneToMany(mappedBy = "category")
    private List<Videos_24162121> videos;
}
