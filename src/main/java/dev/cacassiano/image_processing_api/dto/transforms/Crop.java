package dev.cacassiano.image_processing_api.dto.transforms;

public record Crop (
    Integer xini,
    Integer yini,
    Integer xfin,
    Integer yfin
){
}