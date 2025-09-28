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

@RestController
@RequestMapping("/images/filters")
public class FiltersControler {

    @Autowired
    private FiltersService service;
    @Autowired
    private ResponseService responseService;

    // TODO ajustes quanto a como lidar com png's
    @PostMapping(value = "/blackAndWhite", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> filterBlackAndWhite(MultipartFile image, String format) throws IOException {
        if (image == null) {
            return ResponseEntity.badRequest().build();
        }
        byte[] newImage = service.toBlackAndWhite(ImageIO.read(image.getInputStream()), format);
        return responseService.createImageResponse(newImage, format);
    }

    @PostMapping(value = "/sepia" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> filterSepia(MultipartFile image, String format) throws IOException {
        if (image == null) {
            return ResponseEntity.badRequest().build();
        }
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

    @PostMapping(value = "/bgRemove", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> removeBackground(MultipartFile image) throws IOException {
        
        if (image == null) {
            return ResponseEntity.badRequest().build();
        }

        byte[] myImage = service.removeBack(image);
        // Return png because is the unique image type that acepts transparency
        return responseService.createImageResponse(myImage, "png");
    }
}
