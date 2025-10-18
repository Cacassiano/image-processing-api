package dev.cacassiano.image_processing_api.service;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

@Service
public class ImageConversorService {
    public byte[] imageToByteArray(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream storage = new ByteArrayOutputStream();
        ImageIO.write(image, format, storage);
        if(storage.toByteArray().length < 2) throw new IOException("Error while creating image bytes");
        return storage.toByteArray();
    }

    public byte[] convert(BufferedImage image, String destFormat) throws IOException {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                ImageTypes.valueOf(destFormat.toUpperCase()).getValue()
        );

        newImage.createGraphics().drawImage(image, 0, 0, Color.white, null);
        return this.imageToByteArray(newImage, destFormat);
    }
}
