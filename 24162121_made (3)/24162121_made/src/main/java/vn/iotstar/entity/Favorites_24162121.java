package vn.iotstar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity(name = "Favorites")
@Table(name = "Favorites")
public class Favorites_24162121 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FavoriteId")
    private int favoriteId;

    @Column(name = "LikedDate")
    private LocalDate likedDate;

    @ManyToOne
    @JoinColumn(name = "Username")
    private Users_24162121 user;

    @ManyToOne
    @JoinColumn(name = "VideoId")
    private Videos_24162121 video;
}
