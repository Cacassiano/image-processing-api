package dev.cacassiano.image_processing_api.dto;

import org.springframework.web.multipart.MultipartFile;

import dev.cacassiano.image_processing_api.exceptions.custom.SupportedTypesException;
import dev.cacassiano.image_processing_api.service.SupportedTypes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ImageRequestDTO {
    @NotNull(message="image is null") 
    private final MultipartFile image; 
    @NotBlank(message="there is no format") 
    private final String format;

    public ImageRequestDTO(MultipartFile image, String format) throws SupportedTypesException {
        try {
            SupportedTypes.valueOf(format.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new SupportedTypesException("The given format value '"+format+"' is not supported");
        }
        
        this.format = format.toLowerCase();
        this.image = image;
    }
}
