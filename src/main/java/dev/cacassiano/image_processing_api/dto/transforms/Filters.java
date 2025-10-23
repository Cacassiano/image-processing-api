package dev.cacassiano.image_processing_api.dto.transforms;

public record Filters(
    Boolean grayscale,
    Boolean sepia,
    Integer black_intesity,
    Integer sepia_saturation,
    Boolean remove_background
) {

}