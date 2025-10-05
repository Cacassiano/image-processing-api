package dev.cacassiano.image_processing_api.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dev.cacassiano.image_processing_api.service.interfaces.ImageToByteConversor;


@Service
public class FiltersService extends ImageToByteConversor{

    @Value("${remove-bg_API_URL}")
    private String url;
    @Value("${remove-bg_API_KEY}")
    private String key;
    
    public byte[] toBlackAndWhite(BufferedImage image, String format) throws IOException {
        // Iterate over all pixels in the image
        for (int i = 0; i< image.getWidth();i++) {
            for (int j = 0; j< image.getHeight(); j++) {
                // Get the current pixel, as a ABGR color
                Color pixelRGB = new Color(image.getRGB(i, j), true);
                // checks if is not a transparent pixel
                if(pixelRGB.getAlpha() == 0) continue;

                // Makes a avareage of the color to determine
                // your brigthness
                int avg = (pixelRGB.getRed() + pixelRGB.getGreen()+ pixelRGB.getBlue())/3;

                // Sets the same value of Red, Green, Blue as the avarege value above
                // with no saturation the color just can be Black, White e gray
                int newColor = new Color(avg, avg, avg).getRGB();
                image.setRGB(i, j,newColor);        
            }
        }
        // Return the image bytes
        return this.imageToByteArray(image, format);
    }

    public byte[] toSepia(BufferedImage image, String format, int saturation) throws IOException {
        int maxX = image.getWidth(), maxY = image.getHeight();
        final int maxRGB = 255; 

        // Iterate over all image's pixels
        for(int i = 0; i<maxY;i++) {
            for (int j=0; j<maxX;j++){
                // Get the original pixel RGB object
                int rgbOriginal = image.getRGB(j,i);

                // Create a Color with Red, Green, Blue and Alpha from the 
                // RGB object above
                Color color = new Color(rgbOriginal, true);
                // Checks if is not a translucent pixel
                if(color.getAlpha() == 0) continue;

                // Makes a avarage of the colors
                int avg = (color.getRed() + color.getBlue() + color.getGreen())/3;
                // Sets blue with avg
                int blue = avg;
                // Sets red as avg + intensity to make the image more red
                int red = Math.min(avg+saturation, maxRGB);
                // Sets green as avg + intesity/3 to make the image more yellow/orange
                int green = Math.min(avg+saturation/3, maxRGB);

                // Creates a new color with que new R, G & B values 
                color = new Color(red,green, blue);
                // the new color to the image pixel
                image.setRGB(j,i, color.getRGB());
            }
        }
        // Return the image bytes
        return this.imageToByteArray(image, format);
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
        // Create the multipart entity to send
        HttpEntity entity = MultipartEntityBuilder.create()
            .addBinaryBody("image_file", image.getBytes())
            .addTextBody("size", "auto")
            .build();
        // Send the request with the multipart entity as payload
        // and get the image bytes
        byte[] response = Request.Post(url)
            .addHeader("X-Api-Key", key)
            .body(entity)
            .execute().returnContent().asBytes();
        // Return the image bynaries
        return response;
    }
    
}
