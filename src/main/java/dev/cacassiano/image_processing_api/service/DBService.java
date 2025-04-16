package dev.cacassiano.image_processing_api.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.cacassiano.image_processing_api.dto.ImageResponseDTO;
import dev.cacassiano.image_processing_api.entity.ImageEntity;
import dev.cacassiano.image_processing_api.repository.ImageRepository;

@Service
public class DBService {

    @Autowired
    private ImageRepository repository;
 
    public String saveNewImage(String format, String title, String dono, InputStream image) throws IOException {
        ImageEntity newImage = new ImageEntity(image.readAllBytes() ,format,title, dono);
        try {
            repository.save(newImage);
            return "saved";    
        } catch (Exception e) {
            return "problems while saving the new image in db";
        } 
    }

    public List<ImageResponseDTO> getAllImages(String dono) {
        List<ImageEntity> images = repository.findAll();
        List<ImageResponseDTO> myImages= new ArrayList<>();
        images.forEach((ImageEntity) -> {
            if(ImageEntity.getDono().equals(dono)) {
                myImages.add(new ImageResponseDTO(ImageEntity));
            }
        } );

        return myImages;
    }

    public boolean deleteImageById(String id, String dono) {
        ImageEntity image = repository.getReferenceById(id);
        if (image == null || !image.getDono().equals(dono)) {
            return false;
        }
        repository.delete(image);
        return true;
    }
}
