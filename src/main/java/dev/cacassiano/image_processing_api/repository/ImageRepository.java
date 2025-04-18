package dev.cacassiano.image_processing_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.cacassiano.image_processing_api.entity.ImageEntity;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, String> {
    
}
