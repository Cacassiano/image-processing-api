package dev.cacassiano.image_processing_api.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dev.cacassiano.image_processing_api.dto.ResponseEntityImage;

@Service
public class FiltersService extends ResponseEntityImage{

    public ResponseEntity<byte[]> toBlackAndWhite(BufferedImage image, String format) throws IOException {
        
        for (int i = 0; i< image.getWidth();i++) {
            for (int j = 0; j< image.getHeight();j++) {
                Color pixelRGB = new Color(image.getRGB(i, j));

                int newTone = (pixelRGB.getRed() + pixelRGB.getGreen()+ pixelRGB.getBlue())/3;

                int newRGB = new Color(newTone, newTone, newTone).getRGB();
                image.setRGB(i, j,newRGB);        
            }
        }

        return this.imagemResponseDTO(image, format);
    }
    
}
