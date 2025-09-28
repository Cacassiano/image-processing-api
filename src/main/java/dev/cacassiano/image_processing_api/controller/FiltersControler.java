package dev.cacassiano.image_processing_api.controller;

import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cacassiano.image_processing_api.dto.ImageRequestDTO;
import dev.cacassiano.image_processing_api.service.FiltersService;
import dev.cacassiano.image_processing_api.service.ResponseService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/images/filters")
public class FiltersControler {

    @Autowired
    private FiltersService service;
    @Autowired
    private ResponseService responseService;

    // TODO ajustes quanto a como lidar com png's
    @PostMapping(value = "/black-and-white", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> filterBlackAndWhite(@Valid ImageRequestDTO dto) throws IOException {
        
        byte[] newImage = service.toBlackAndWhite(ImageIO.read(dto.getImage().getInputStream()), dto.getFormat());
        return responseService.createImageResponse(newImage, dto.getFormat());
    }

    @PostMapping(value = "/sepia" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> filterSepia(@Valid ImageRequestDTO dto) throws IOException {
        
        byte[] newImage = service.toSepia(ImageIO.read(dto.getImage().getInputStream()), dto.getFormat()); 
        return responseService.createImageResponse(newImage, dto.getFormat());
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
    public ResponseEntity<byte[]> removeBackground(@Valid ImageRequestDTO dto) throws IOException {
        
        byte[] myImage = service.removeBack(dto.getImage());
        // Return png because is the unique image type that acepts transparency
        return responseService.createImageResponse(myImage, "png");
    }
}
