package dev.cacassiano.image_processing_api.controller;

import java.io.IOException;

import javax.imageio.ImageIO;

import dev.cacassiano.image_processing_api.dto.ImageUploadDTO;
import dev.cacassiano.image_processing_api.dto.ImageUploadRespDTO;
import dev.cacassiano.image_processing_api.service.ImageConversorService;
import dev.cacassiano.image_processing_api.service.interfaces.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        String id = storageService.saveImage(
                req.getImage().getInputStream(),
                req.getFormat(),
                req.getName()
        );

        return ResponseEntity.ok(new ImageUploadRespDTO(id));
    }

    @PostMapping(value = "/mirror", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> mirrorEndpoint(@Valid ImageRequestDTO dto) throws IOException {
        byte[] newImage = imageTransformService.mirrorImage(ImageIO.read(dto.getImage().getInputStream()), dto.getFormat());
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
        
        byte[] newImage = imageTransformService.rescaleImage(ImageIO.read(
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
        
        byte[] newImage = imageTransformService.rotateImage(
            ImageIO.read(dto.getImage().getInputStream()),
            inclinationInDegrees, 
            dto.getFormat()
        );
        return responseService.createImageResponse(newImage, dto.getFormat());
    }

    @PostMapping(value = "/convert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> convertImage(@Valid ImageRequestDTO dto) throws IOException {
        
        byte[] newImage = conversor.convert(
            ImageIO.read(dto.getImage().getInputStream()), 
            dto.getFormat()
        );
        return responseService.createImageResponse(newImage, dto.getFormat());
    }
}
