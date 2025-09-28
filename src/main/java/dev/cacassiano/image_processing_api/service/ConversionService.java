package dev.cacassiano.image_processing_api.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.springframework.stereotype.Service;

import dev.cacassiano.image_processing_api.service.interfaces.ImageToByteConversor;

@Service
public class ConversionService extends ImageToByteConversor{

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