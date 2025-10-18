package dev.cacassiano.image_processing_api.dto;

public record TransformDTO(
    Crop crop,
    Rescale rescale,
    String output,
    Double rotation,
    Filters filters
) {

}

record Crop (
    Long xini,
    Long yini,
    Long xfin,
    Long yfin
){
}


record Rescale(
    Integer xscale,
    Integer yscale
) {

}

record Filters(
    Boolean grayscale,
    Boolean sepia
) {

}