package dev.cacassiano.image_processing_api.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/images/filters")
public class FiltersControler {

    @Autowired
    private FiltersService service;
    @Autowired
    private ResponseService responseService;

    @PostMapping(value = "/black-and-white", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> filterBlackAndWhite(@Valid ImageRequestDTO dto) throws IOException {
        InputStream imageInputStream = dto.getImage().getInputStream();
        BufferedImage image = ImageIO.read(imageInputStream);
        service.toBlackAndWhite(image, dto.getFormat());

        return responseService.createImageResponse(image, dto.getFormat());
    }

    // TODO DTO's separados
    @PostMapping(value = "/sepia" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> filterSepia(
            @Valid 
            ImageRequestDTO dto,
            @Valid @Min(value = 0l, message="The min value of intesity is 0") @Max(value = 255l, message="The max value of intensity is 255") 
            Integer saturation
        ) throws IOException {

        InputStream imageInputStream = dto.getImage().getInputStream();
        BufferedImage image = ImageIO.read(imageInputStream);

        service.toSepia(image, dto.getFormat(), saturation);
        return responseService.createImageResponse(image, dto.getFormat());
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
        
        BufferedImage myImage = service.removeBack(dto.getImage());

        // Return png because is the unique image type that acepts transparency
        return responseService.createImageResponse(myImage, "png");
    }
}
