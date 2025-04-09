package dev.cacassiano.image_processing_api.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cacassiano.image_processing_api.service.ImageService;

@RestController

public class ImageController {
    @Autowired
    private ImageService service;

    @PostMapping(value = "/mirror", consumes = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> mirrorEndpoint(InputStream req) throws IOException{
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(service.mirrorImage(ImageIO.read(req)));
    }
}
