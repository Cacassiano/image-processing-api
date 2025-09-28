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

import dev.cacassiano.image_processing_api.dto.ImageRequestDTO;
import dev.cacassiano.image_processing_api.service.ConversionService;
import dev.cacassiano.image_processing_api.service.ImageService;
import dev.cacassiano.image_processing_api.service.ResponseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

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
    public ResponseEntity<byte[]> mirrorEndpoint(@Valid ImageRequestDTO dto) throws IOException {
       
         byte[] newImage = imageService.mirrorImage(ImageIO.read(dto.getImage().getInputStream()), dto.getFormat());
        return responseService.createImageResponse(newImage, dto.getFormat());
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
        
        byte[] newImage = imageService.rescaleImage(ImageIO.read(
            dto.getImage().getInputStream()), 
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
        
        byte[] newImage = imageService.rotateImage(ImageIO.read(
            dto.getImage().getInputStream()), 
            inclinationInDegrees, 
            dto.getFormat()
        );
        return responseService.createImageResponse(newImage, dto.getFormat());
    }

    @PostMapping(value = "/convert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // TODO validar a entrada com bean validation e usar ENUM de tipos aceitos
    public ResponseEntity<byte[]> convertImage(@Valid ImageRequestDTO dto) throws IOException {
        
        byte[] newImage = conversionService.convert(
            ImageIO.read(dto.getImage().getInputStream()), 
            dto.getFormat()
        );
        return responseService.createImageResponse(newImage, dto.getFormat());
    }
}
