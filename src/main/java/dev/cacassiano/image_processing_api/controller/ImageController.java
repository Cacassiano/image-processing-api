package dev.cacassiano.image_processing_api.controller;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import dev.cacassiano.image_processing_api.dto.ImageUploadDTO;
import dev.cacassiano.image_processing_api.dto.ImageUploadRespDTO;
import dev.cacassiano.image_processing_api.dto.TransformDTO;
import dev.cacassiano.image_processing_api.dto.transforms.Filters;
import dev.cacassiano.image_processing_api.entity.Image;
import dev.cacassiano.image_processing_api.exceptions.custom.NotFoundException;
import dev.cacassiano.image_processing_api.service.FiltersService;
import dev.cacassiano.image_processing_api.service.ImageConversorService;
import dev.cacassiano.image_processing_api.service.interfaces.ImageStorageService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.cacassiano.image_processing_api.service.ImageTransformService;
import dev.cacassiano.image_processing_api.service.ResponseService;
import jakarta.validation.Valid;

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
    @Autowired
    private FiltersService filtersService;

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

    @PostMapping("/{image_id}/transform")
    public ResponseEntity<byte[]> transformImage(
            @RequestBody
            TransformDTO req,
            @PathVariable @NotBlank
            String image_id
            ) throws IOException, NotFoundException {
        Image imageEntity = storageService.findImageById(image_id);
        BufferedImage image = ImageIO.read(new File(imageEntity.getUrl()));

        if(req.getCrop() != null){
            image = imageTransformService.cropImage(
                image,
                req.getCrop()
            );
        }

        if (req.getRescale() != null) {
            image = imageTransformService.rescaleImage(
                    image,
                    req.getRescale().xscale(),
                    req.getRescale().yscale()
            );
        }
        if (req.getMirror() != null && req.getMirror()) {
            image = imageTransformService.mirrorImage(image);
        }
        if (req.getRotation() != null) {
            image = imageTransformService.rotateImage(
                image,
                req.getRotation()
            );
        }

        if (req.getFilters() != null) {
            Filters filters = req.getFilters();
            if(filters.grayscale() != null && filters.grayscale()) filtersService.toBlackAndWhite(image, filters.black_intesity());
            if(filters.sepia() != null && filters.sepia()) filtersService.toSepia(image, filters.sepia_saturation());
//            if(filters.remove_background() != null && filters.remove_background()) {
//                image = filtersService.removeBack(conversor.imageToByteArray(image, "png"));
//            }
        }
        if (!req.getOutput().equals(imageEntity.getFormat())) {
            image = conversor.convert(image, req.getOutput());
        }
        System.out.println("Enviando response");
        return responseService.createImageResponse(image, req.getOutput());
    }
}