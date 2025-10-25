package dev.cacassiano.image_processing_api.service;

import dev.cacassiano.image_processing_api.entity.Image;
import dev.cacassiano.image_processing_api.entity.User;
import dev.cacassiano.image_processing_api.exceptions.custom.NotFoundException;
import dev.cacassiano.image_processing_api.infra.secutity.TokenService;
import dev.cacassiano.image_processing_api.repository.ImageRepository;
import dev.cacassiano.image_processing_api.repository.UserRepository;
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
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public String saveImage(InputStream imageInputStream, String format, String name, String userToken) throws IOException, NotFoundException {
        // Create uuid to avoid name conflicts
        String id = UUID.randomUUID().toString();

        String email = tokenService.validateToken(userToken.replace("Bearer ", ""));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));

        // Create directory if dont exists
        File storage = new File("storage");
        if(!storage.exists()) {
            System.out.println("create the storage: " + storage.mkdir());
        }
        // Create a file object inside storage directory
        File imageFile = new File("storage/"+id+"."+format);

        try {
            // Write the image in the file
            ImageIO.write(
                ImageIO.read(imageInputStream),
                format,
                imageFile
            );

            // Create the file
            System.out.println("Creating file:"+ imageFile.createNewFile());

            // Checks if file exists
            if(!imageFile.exists()) throw new IOException("Error while saving the file");

            // create the image entity
            Image image = new Image("storage/"+id+"."+format, format, name, user);
            // try save in db
            image = repository.save(image);

            // return it id
            return image.getId();
        } catch (IOException ex) {
            System.out.println("Deleted the file: "+imageFile.delete());
            throw ex;
        }
    }

    @Override
    public Image findImageById(String id, String userToken) throws NotFoundException, IOException{
        String email = tokenService.validateToken(userToken.replace("Bearer ", ""));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
        // Get image from db
        Image image = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Image Not Found"));

        if(!image.getUser().getId().equals(user.getId())) throw new NotFoundException("Invalid token");
        return image;
    }
}
