package dev.cacassiano.image_processing_api.dto;

import dev.cacassiano.image_processing_api.exceptions.custom.SupportedTypesException;
import dev.cacassiano.image_processing_api.service.enums.SupportedTypes;

public class TransformDTO {
    private Crop crop;
    private Rescale rescale;
    private String output;
    private Double rotation;
    private Filters filters;

    public TransformDTO(
            Crop crop,
            Rescale rescale,
            Filters filters,
            Double rotation,
            String output
    ) throws SupportedTypesException {
        // if(crop) @Valid crop;
        this.crop = crop;
        this.rescale = rescale;
        this.filters = filters;
        this.rotation = rotation;

        try {
            SupportedTypes.valueOf(output.toUpperCase());
        }catch(Exception e) {
            throw new SupportedTypesException("The output value: " + output + " is not supported yet");
        }

        this.output = output;
    }
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