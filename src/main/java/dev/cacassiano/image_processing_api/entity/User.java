package dev.cacassiano.image_processing_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class User {
    @Id @GeneratedValue(strategy = GenerationType.UUID) @Column(unique = true, nullable = false)
    String id;

    @Column(nullable = false)
    String password;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String username;
}
