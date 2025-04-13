package dev.cacassiano.image_processing_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cacassiano.image_processing_api.entity.User;

public interface UserRepository extends JpaRepository<String, User>
{
    
}
