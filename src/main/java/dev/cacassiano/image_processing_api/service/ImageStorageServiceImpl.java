package dev.cacassiano.image_processing_api.service;

import dev.cacassiano.image_processing_api.entity.Image;
import dev.cacassiano.image_processing_api.repository.ImageRepository;
import dev.cacassiano.image_processing_api.service.interfaces.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;


@Service
public class ImageStorageServiceImpl implements ImageStorageService {

    @Autowired
    private ImageRepository repository;

    @Override
    public String saveImage(InputStream imageInputStream, String format, String name) throws IOException {
        String id = UUID.randomUUID().toString();
        File imageFile = new File("storage/"+id+"."+format);

        try {
            ImageIO.write(
                    ImageIO.read(imageInputStream),
                    format,
                    imageFile
            );

            System.out.println("File exists: "+imageFile.exists());
            System.out.println("Creating file:"+ imageFile.createNewFile());

            Image image = new Image(id+"."+format, format, name);
            image = repository.save(image);

            return image.getId();
        } catch (IOException ex) {
            System.out.println("Deleted the file: "+imageFile.delete());
            throw ex;
        }
    }

    @Override
    public BufferedImage findImageById(String id) {
        return null;
    }
}
