package dev.cacassiano.image_processing_api.controller;

import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dev.cacassiano.image_processing_api.service.FiltersService;

@RestController
@RequestMapping("/filter")
public class FiltersControler {

    @Autowired
    private FiltersService service;

    @PostMapping(value = "/blackAndWhite", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> filterBlaxkAndWhite(MultipartFile image, String format) throws IOException {
        if (image == null) {
            return ResponseEntity.badRequest().build();
        }
        return service.toBlackAndWhite(ImageIO.read(image.getInputStream()), format);
    }

    
}
