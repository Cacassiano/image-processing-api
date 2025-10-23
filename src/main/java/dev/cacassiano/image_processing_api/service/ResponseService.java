package dev.cacassiano.image_processing_api.service;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    @Autowired
    private ImageConversorService conversor;

    public ResponseEntity<byte[]> createImageResponse(BufferedImage image, String format) throws IOException {
        byte[] byteImage = conversor.imageToByteArray(image, format);

        System.out.println("Is png: "+ format.equals("png"));
        System.out.println("Is jpeg: "+format.equals("jpeg"));
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
