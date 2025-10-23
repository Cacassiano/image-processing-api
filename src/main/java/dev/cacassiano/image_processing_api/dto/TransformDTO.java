package dev.cacassiano.image_processing_api.dto;

import dev.cacassiano.image_processing_api.dto.transforms.Crop;
import dev.cacassiano.image_processing_api.dto.transforms.Filters;
import dev.cacassiano.image_processing_api.dto.transforms.Rescale;
import dev.cacassiano.image_processing_api.exceptions.custom.SupportedTypesException;
import dev.cacassiano.image_processing_api.service.enums.SupportedTypes;
import lombok.Getter;

import java.util.Locale;

@Getter
public class TransformDTO {
    private final Crop crop;
    private final Rescale rescale;
    private final String output;
    private final Double rotation;
    private final Filters filters;
    private final Boolean mirror;

    public TransformDTO(
            Crop crop,
            Rescale rescale,
            Filters filters,
            Double rotation,
            Boolean mirror,
            String output
    ) throws SupportedTypesException {
        this.crop = crop;
        this.rescale = rescale;
        this.filters = filters;
        this.rotation = rotation;
        this.mirror = mirror;
        try {
            SupportedTypes.valueOf(output.toUpperCase());
        }catch(Exception e) {
            throw new SupportedTypesException("The output value: " + output + " is not supported yet");
        }
        this.output = output.toLowerCase();
    }
}