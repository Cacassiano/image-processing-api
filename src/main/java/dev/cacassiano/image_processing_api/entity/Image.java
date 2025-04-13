package dev.cacassiano.image_processing_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Image")
@Table(name = "image")
@Setter
@Getter
@NoArgsConstructor
public class Image {
    @Id @GeneratedValue(strategy = GenerationType.UUID) @Column(unique = true, nullable = false)
    String id;

    @Column(nullable = false)
    byte[] image;

    @Column(nullable = false)
    String dono;

}
