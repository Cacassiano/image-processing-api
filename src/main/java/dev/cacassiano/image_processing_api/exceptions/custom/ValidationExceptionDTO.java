package dev.cacassiano.image_processing_api.exceptions.custom;

import java.util.List;

public record ValidationExceptionDTO(
    String error,
    List<String> messages
) {
    
}
