package dev.cacassiano.image_processing_api.controller;

import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.cacassiano.image_processing_api.dto.FormDataDTO;
import dev.cacassiano.image_processing_api.service.ImageService;

@RestController

public class ImageController {
    @Autowired
    private ImageService service;

    @PostMapping("/mirror")
    public Byte[] mirrorEndpoint(@RequestBody FormDataDTO req) throws IOException{
        return service.mirrorImage(ImageIO.read(req.img()));
    }
}
