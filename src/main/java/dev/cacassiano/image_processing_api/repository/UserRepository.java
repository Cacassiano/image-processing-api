package dev.cacassiano.image_processing_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.cacassiano.image_processing_api.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
   
    Optional<User> findByEmail(String email);
}
