package dev.cacassiano.image_processing_api.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.execchain.RequestAbortedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dev.cacassiano.image_processing_api.dto.ImageConversion;

@Service
public class FiltersService extends ImageConversion{

    @Value("${remove-bg.api.url}")
    private String url;
    @Value("${remove-bg.api.key}")
    private String key;

    @Autowired
    ImageService imageService;
    
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
    /* codigo defeituoso
        public ResponseEntity<byte[]> toBlur(BufferedImage image, String format) throws IOException {
            final int x = image.getWidth(), y = image.getHeight();
            Integer currentRGB = null;
            int same = 0;
            for (int i = 0; i<y; i++) {
                for (int j = 0; j<x;j++) {
                    if (currentRGB == null || same >= y/10) {
                        same = 0;
                        currentRGB = image.getRGB(j, i);
                        image.setRGB(j, i, currentRGB);
                        same++;
                    } else {
                        image.setRGB(j,i, currentRGB);
                        same++;
                    }
                }

            }
    
          return this.imagemResponseDTO(image, format);
        }
    */

    public byte[] removeBack(MultipartFile image) throws IOException {
        try {
            HttpEntity entity = MultipartEntityBuilder.create()
                                    .addBinaryBody("image_file", image.getBytes())
                                    .addTextBody("size", "auto")
                                    .build();
            byte[] response = Request.Post(url)
                                .addHeader("X-Api-Key", key)
                                .body(entity)
                                .execute().returnContent().asBytes();
            return response;
        } catch (RequestAbortedException e) {
            return null;
        }
        
    }
    
}
