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

    public ResponseEntity<byte[]> toSepia(BufferedImage image, String format) throws IOException {
        int maxX = image.getWidth(), maxY = image.getHeight();
        final int maxRGB = 255, redIntensity  =60;
        
        for(int i = 0; i<maxY;i++) {
            for (int j=0; j<maxX;j++){
                int rgbOriginal = image.getRGB(j,i);
                Color color = new Color(rgbOriginal);

                int avg = (color.getRed() + color.getBlue() + color.getGreen())/3;
                int blue = avg;
                int red = Math.min(avg+redIntensity, maxRGB);
                int green = Math.min(avg+redIntensity/3, maxRGB);

                color = new Color(red,green, blue);
                image.setRGB(j,i, color.getRGB());
            }
        }

        return this.imagemResponseDTO(image, format);
    }
    
}
