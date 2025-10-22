package dev.cacassiano.image_processing_api.controller;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import dev.cacassiano.image_processing_api.dto.ImageUploadDTO;
import dev.cacassiano.image_processing_api.dto.ImageUploadRespDTO;
import dev.cacassiano.image_processing_api.entity.Image;
import dev.cacassiano.image_processing_api.exceptions.custom.NotFoundException;
import dev.cacassiano.image_processing_api.service.ImageConversorService;
import dev.cacassiano.image_processing_api.service.interfaces.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cacassiano.image_processing_api.dto.ImageRequestDTO;
import dev.cacassiano.image_processing_api.service.ImageTransformService;
import dev.cacassiano.image_processing_api.service.ResponseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/images")
// TODO tests unitarios
public class ImageController {
    
    @Autowired
    private ImageConversorService conversor;
    @Autowired
    private ImageTransformService imageTransformService;
    @Autowired
    private ResponseService responseService;
    @Autowired
    private ImageStorageService storageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageUploadRespDTO> uploadImage(@Valid ImageUploadDTO req) throws IOException {
        InputStream imageInputStream = req.getImage().getInputStream();

        String id = storageService.saveImage(
                imageInputStream,
                req.getFormat(),
                req.getName()
        );

        return ResponseEntity.ok(new ImageUploadRespDTO(id));
    }

    @PostMapping(value = "/mirror/{imgId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> mirrorEndpoint(@PathVariable String imgId) throws NotFoundException, IOException {
        Image img = storageService.findImageById(imgId);
        File imgFile = new File("storage/"+img.getUrl());

        BufferedImage newImage = imageTransformService.mirrorImage(ImageIO.read(imgFile), img.getFormat());
        return responseService.createImageResponse(newImage, img.getFormat());
    }

    @PostMapping(value = "/scale", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) 
    public ResponseEntity<byte[]> scaleImage(
            @Valid 
            ImageRequestDTO dto,
            @Valid @NotNull(message="x scale is null") 
            Float scaleX, 
            @Valid @NotNull(message="y scale is null")
            Float scaleY
        ) throws IOException{

        InputStream imageInputStream = dto.getImage().getInputStream();
        BufferedImage image = ImageIO.read(imageInputStream);

        BufferedImage newImage = imageTransformService.rescaleImage(
            image,
            dto.getFormat(), 
            scaleX, 
            scaleY
        );
        return responseService.createImageResponse(newImage, dto.getFormat());
    }

    @PostMapping(value = "/rotate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> rotateImage(
            @Valid 
            ImageRequestDTO dto,
            @Valid @NotNull(message="Inclination angle is null") 
            Double inclinationInDegrees
        ) throws IOException {

        InputStream imageInputStream = dto.getImage().getInputStream();
        BufferedImage image = ImageIO.read(imageInputStream);

        BufferedImage newImage = imageTransformService.rotateImage(
            image,
            inclinationInDegrees, 
            dto.getFormat()
        );
        return responseService.createImageResponse(newImage, dto.getFormat());
    }

    @PostMapping(value = "/convert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> convertImage(@Valid ImageRequestDTO dto) throws IOException {
        InputStream imageInputStream = dto.getImage().getInputStream();
        BufferedImage image = ImageIO.read(imageInputStream);

        BufferedImage newImage = conversor.convert(
            image,
            dto.getFormat()
        );
        return responseService.createImageResponse(newImage, dto.getFormat());
    }
}
