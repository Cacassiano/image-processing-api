package dev.cacassiano.image_processing_api.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dev.cacassiano.image_processing_api.dto.ImageIdDTO;
import dev.cacassiano.image_processing_api.dto.ImageResponseDTO;
import dev.cacassiano.image_processing_api.infra.secutity.TokenService;
import dev.cacassiano.image_processing_api.service.DBService;

@RestController
@RequestMapping("/image")
public class DBController {
    
    @Autowired 
    private TokenService tkService;
    @Autowired
    private DBService service;

    @GetMapping
    public List<ImageResponseDTO> getAll(@RequestHeader("Authorization") String bearerToken) {
        String dono = tkService.validateToken(bearerToken.replace("Bearer ", ""));
        return service.getAllImages(dono);
    }

    @PostMapping(value = "/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> saveImage(MultipartFile image, @RequestHeader(value = "Authorization") String token, String format, String title) throws IOException {
        if (image != null) {
            
            String dono = tkService.validateToken(token.replace("Bearer ", ""));
            String response = service.saveNewImage(format, title,dono, image.getInputStream());

            if(response.equals("saved")) {
                return ResponseEntity.ok().body("Image saved in database");
            } else {
                return ResponseEntity.internalServerError().body(response);
            }
        }
        return ResponseEntity.badRequest().body("Image not found");
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> deleteImage(@RequestBody ImageIdDTO id, @RequestHeader("Authorization") String bearerToken) {        
        if (id != null) {
            String dono = tkService.validateToken(bearerToken.replace("Bearer ", ""));
            boolean deleted = service.deleteImageById(id.id(), dono);
            if(deleted) {
                return ResponseEntity.ok().body("Image has deleted sucessfuly");
            } else {
                return ResponseEntity.internalServerError().body("Error while deleting the image");
            }
        }
        return ResponseEntity.badRequest().body("Required information hasn't give");
    }
}
