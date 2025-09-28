package dev.cacassiano.image_processing_api.service;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    // meter tratamento de excessão
    public ResponseEntity<byte[]> createImageResponse(byte[] byteImage, String format) throws IOException {
        if (byteImage.length < 2) return ResponseEntity.unprocessableEntity().build();

        if (format.equals("png")) {
            return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(byteImage);
        }
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(byteImage);  
    }
}
