package dev.cacassiano.image_processing_api.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dev.cacassiano.image_processing_api.entity.Image;
import dev.cacassiano.image_processing_api.repository.ImageRepository;

@Service
public class DBService {

    @Autowired
    private ImageRepository repository;
 
    public String saveNewImage(String format, String title, String dono, InputStream image) throws IOException {
        Image newImage = new Image(image.readAllBytes() ,format,title, dono);
        try {
            repository.save(newImage);
            return "saved";    
        } catch (Exception e) {
            return "problems while saving the new image in db";
        } 
    }

    public List<ResponseEntity<byte[]>> getAllImages() {
        List<Image> images = repository.findAll();
        List<ResponseEntity<byte[]>> myImages= new ArrayList<>();
        for(int i = 0; i<images.size(); i++) {
            if (images.get(i).getFormat() == "png") {
                myImages.add( ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "image/png")
                        .body(images.get(i).getImage())
                    );
                } else {
                myImages.add(ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                        .body(images.get(i).getImage())
                );
            }
        }
        return myImages;
    }
}
