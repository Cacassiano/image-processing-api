package dev.cacassiano.image_processing_api.service.interfaces;

import dev.cacassiano.image_processing_api.entity.Image;
import dev.cacassiano.image_processing_api.exceptions.custom.NotFoundException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public interface ImageStorageService {
    public String saveImage(InputStream imageInputStream, String format, String name, String userToken) throws IOException, NotFoundException;
    public Image findImageById(String id, String userToken)throws NotFoundException, IOException;
}
