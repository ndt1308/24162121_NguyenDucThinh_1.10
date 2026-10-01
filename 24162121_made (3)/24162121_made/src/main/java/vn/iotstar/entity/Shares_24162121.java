package vn.iotstar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity(name = "Shares")
@Table(name = "Shares")
public class Shares_24162121 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ShareId")
    private int shareId;

    @Column(name = "Emails", columnDefinition = "nvarchar(50)")
    private String emails;

    @Column(name = "SharedDate")
    private LocalDate sharedDate;

    @ManyToOne
    @JoinColumn(name = "Username")
    private Users_24162121 user;

    @ManyToOne
    @JoinColumn(name = "VideoId")
    private Videos_24162121 video;
}
