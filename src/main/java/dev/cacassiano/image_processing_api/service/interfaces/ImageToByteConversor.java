package dev.cacassiano.image_processing_api.service.interfaces;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;


public abstract class ImageToByteConversor {
    public byte[] imageToByteArray(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream storage = new ByteArrayOutputStream();
        ImageIO.write(image, format, storage);
        if(storage.toByteArray().length < 2) throw new IOException("Error while creating image bytes");
        return storage.toByteArray();
    }   
}
