package vn.iotstar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity(name = "Users")
@Table(name = "Users")
public class Users_24162121 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Username", columnDefinition = "nvarchar(50)")
    private String username;

    @Column(name = "Password", columnDefinition = "nvarchar(50)")
    private String password;

    @Column(name = "Phone", columnDefinition = "nvarchar(15)")
    private String phone;

    @Column(name = "Fullname", columnDefinition = "nvarchar(50)")
    private String fullname;

    @Column(name = "Email", columnDefinition = "nvarchar(150)")
    private String email;

    @Column(name = "Admin")
    private Boolean admin;

    @Column(name = "Active")
    private Boolean active;

    @Column(name = "Images", columnDefinition = "nvarchar(500)")
    private String images;

    @OneToMany(mappedBy = "user")
    private List<Shares_24162121> shares;

    @OneToMany(mappedBy = "user")
    private List<Favorites_24162121> favorites;
}
