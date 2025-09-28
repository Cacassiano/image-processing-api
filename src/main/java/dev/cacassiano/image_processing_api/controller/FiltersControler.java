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
import dev.cacassiano.image_processing_api.service.ResponseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/images/filters")
public class FiltersControler {

    @Autowired
    private FiltersService service;
    @Autowired
    private ResponseService responseService;

    // TODO ajustes quanto a como lidar com png's
    @PostMapping(value = "/black-and-white", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> filterBlackAndWhite(
            @Valid @NotNull(message="image is null") 
            MultipartFile image, 
            @Valid @NotBlank(message="there is no format") 
            String format
        ) throws IOException {
        
        byte[] newImage = service.toBlackAndWhite(ImageIO.read(image.getInputStream()), format);
        return responseService.createImageResponse(newImage, format);
    }

    @PostMapping(value = "/sepia" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> filterSepia(
            @Valid @NotNull(message="image is null") 
            MultipartFile image, 
            @Valid @NotBlank(message="there is no format") 
            String format
        ) throws IOException {
        
        byte[] newImage = service.toSepia(ImageIO.read(image.getInputStream()), format); 
        return responseService.createImageResponse(newImage, format);
    }

    /*  Unnimplemented yet

        @PostMapping(value = "/blur", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<byte[]> filterBlur(MultipartFile image, String format) throws IOException{
            if (image == null) {
                return ResponseEntity.badRequest().build();
            }
            return service.toBlur(ImageIO.read(image.getInputStream()), format);
        }
    */

    @PostMapping(value = "/remove-background", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> removeBackground(
            @Valid @NotNull(message="image is null") 
            MultipartFile image
        ) throws IOException {
        
        byte[] myImage = service.removeBack(image);
        // Return png because is the unique image type that acepts transparency
        return responseService.createImageResponse(myImage, "png");
    }
}
