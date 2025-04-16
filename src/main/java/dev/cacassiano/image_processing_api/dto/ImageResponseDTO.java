package dev.cacassiano.image_processing_api.dto;

import dev.cacassiano.image_processing_api.entity.ImageEntity;

public record ImageResponseDTO(String title, String format, byte[] image, String id,String dono) {
    public ImageResponseDTO(ImageEntity entity) {
        this(entity.getTitle(), entity.getFormat(), entity.getImage(), entity.getId(), entity.getDono());
    }
}
