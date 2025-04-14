package dev.cacassiano.image_processing_api.entity;

import dev.cacassiano.image_processing_api.infra.secutity.dto.RegisterDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "User")
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    public User(RegisterDTO registerDTO) {
        this.password = registerDTO.password();
        this.username = registerDTO.username();
        this.email = registerDTO.email();
    }

    @Id @GeneratedValue(strategy = GenerationType.UUID) @Column(unique = true, nullable = false)
    String id;
    
    @Column( nullable = false)
    String password;

    @Column( unique = true , nullable = false)
    String email;

    @Column( nullable = false)
    String username;
}
