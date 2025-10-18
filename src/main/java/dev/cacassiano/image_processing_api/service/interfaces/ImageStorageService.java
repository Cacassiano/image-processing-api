package dev.cacassiano.image_processing_api.service.interfaces;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public interface ImageStorageService {
    public String saveImage(InputStream imageInputStream, String format, String name) throws IOException;
    public BufferedImage findImageById(String id);
}
