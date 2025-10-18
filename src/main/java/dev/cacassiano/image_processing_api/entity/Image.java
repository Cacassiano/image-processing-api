package dev.cacassiano.image_processing_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Image")
@Table(name = "Image")
@Setter
@Getter
@NoArgsConstructor
public class Image {

    public Image(String url ,String format, String name) {
        // this.user = userr
        this.url = url;
        this.name = name;
        this.format = format;
    }

    @Id @GeneratedValue(strategy = GenerationType.UUID) @Column(unique = true, nullable = false)
    String id;

    @Column(nullable = false)
    String url;

    @Column(name="name")
    String name;

    @Column(nullable = false)
    String format;

    // @ManyToOne
    // @JoinColumn(name="owner")
    // User user;

}
