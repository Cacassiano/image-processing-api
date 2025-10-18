package dev.cacassiano.image_processing_api.dto;

import java.util.List;

public record ValidationExceptionDTO(
    String error,
    List<String> messages
) {
    
}
