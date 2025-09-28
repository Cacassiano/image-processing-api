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

import dev.cacassiano.image_processing_api.service.ConversionService;
import dev.cacassiano.image_processing_api.service.ImageService;
import dev.cacassiano.image_processing_api.service.ResponseService;

@RestController
@RequestMapping("/images")
public class ImageController {
    
    @Autowired
    private ConversionService conversionService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ResponseService responseService;


    @PostMapping(value = "/mirror", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> mirrorEndpoint(MultipartFile image, String format) throws IOException {
        if (image == null) {
            throw new NullPointerException("Invalid request: image not found");
        }
        byte[] newImage = imageService.mirrorImage(ImageIO.read(image.getInputStream()), format);
        return responseService.createImageResponse(newImage, format);
    }

    @PostMapping(value = "/scale", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) 
    public ResponseEntity<byte[]> scaleImage(MultipartFile image, String format, Float scaleX, Float scaleY) throws IOException{
        if (image == null) {
            throw new NullPointerException("Invalid request: image not found"); 
        }
        byte[] newImage = imageService.rescaleImage(ImageIO.read(image.getInputStream()), format, scaleX, scaleY);
        return responseService.createImageResponse(newImage, format);
    }

    @PostMapping(value = "/rotate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> rotateImage(MultipartFile image, String format, Double inclinationInDegrees) throws IOException {
        if (image == null) {
            throw new NullPointerException("Invalid request: image not found");
        }
        byte[] newImage = imageService.rotateImage(ImageIO.read(image.getInputStream()), inclinationInDegrees, format);
        return responseService.createImageResponse(newImage, format);
    }

    @PostMapping(value = "/convert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // TODO validar a entrada com bean validation e usar ENUM de tipos aceitos
    public ResponseEntity<byte[]> convertTypes(MultipartFile image, String destFormat) throws IOException {
        if (image == null) {
            throw new NullPointerException("Invalid request: image not found");
        }
        byte[] newImage = conversionService.convert(image, destFormat);
        return responseService.createImageResponse(newImage, destFormat);
    }
}
