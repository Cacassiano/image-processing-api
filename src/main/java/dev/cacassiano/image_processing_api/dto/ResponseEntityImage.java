package dev.cacassiano.image_processing_api.dto;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


public abstract class ResponseEntityImage {
    
    private byte[] imageToByteArray(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream storage = new ByteArrayOutputStream();
        ImageIO.write(image, format, storage);
        return storage.toByteArray();
    }   

    public ResponseEntity<byte[]> imagemResponseDTO(BufferedImage image, String format) throws IOException {
        byte[] byteImage = this.imageToByteArray(image, format);
        if (byteImage.length > 1) {
            if (format.equals("png")) {
                return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(byteImage);
            } else {
                return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(byteImage); 
            }
        } else {
            return ResponseEntity.unprocessableEntity().build();
        } 
    }
}
