package dev.cacassiano.image_processing_api.controller;

import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dev.cacassiano.image_processing_api.service.ImageService;

@RestController

public class ImageController {
    @Autowired
    private ImageService service;

    @PostMapping(value = "/mirror", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> mirrorEndpoint(MultipartFile image, String format) throws IOException {
        if (image == null) {
            throw new NullPointerException("Invalid request: no image found");
        }
        return service.mirrorImage(ImageIO.read(image.getInputStream()), format);
    }

    @PostMapping(value = "/scale", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) 
    public ResponseEntity<byte[]> scaleImage(MultipartFile image, String format, Float scaleX, Float scaleY) throws IOException{
        if (image == null) {
            throw new NullPointerException("Invalid request: no image found"); 
        }
        return service.rescaleImage(ImageIO.read(image.getInputStream()), format, scaleX, scaleY);
    }
}
