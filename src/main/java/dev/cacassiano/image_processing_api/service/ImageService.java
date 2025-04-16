package dev.cacassiano.image_processing_api.service;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dev.cacassiano.image_processing_api.dto.ImageConversion;

@Service
public class ImageService extends ImageConversion{

    public ResponseEntity<byte[]> mirrorImage(BufferedImage originalIOImage, String format) throws IOException {
        
        AffineTransform transform = AffineTransform.getScaleInstance(-1, 1);
        transform.translate(-originalIOImage.getWidth(), 0);

        AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage newImage = operation.filter(originalIOImage, null);
        
        return this.imagemResponseDTO(newImage, format);
    }

    public ResponseEntity<byte[]> rescaleImage(BufferedImage original, String format, Float scaleX, Float scaleY) throws IOException {
        AffineTransform transform = AffineTransform.getScaleInstance(scaleX, scaleY);
        transform.scale(transform.getScaleX(), transform.getScaleY());
        
        AffineTransformOp operation; 
        if (format.equals("jpeg")) {
            operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        } else {
            operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
        }
        
        BufferedImage newImage = operation.filter(original, null);
        return this.imagemResponseDTO(newImage, format);
    }

    public ResponseEntity<byte[]> rotateImage(BufferedImage original, Double inclination, String format) throws IOException {
        
        int x = original.getWidth(), y = original.getHeight();
        BufferedImage temp = original;
        if( inclination == 270) {
            inclination = -90.0;
        }
        if (inclination == 90 || inclination == -90) {
            temp = new BufferedImage(y, x, original.getType());
            temp.createGraphics().drawImage(original, null, y, x);
        }

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(inclination), temp.getWidth()/2, temp.getHeight()/2);

        AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage newImage = operation.filter(original, null);
        
        
        return this.imagemResponseDTO(newImage, format);
    }

    public ResponseEntity<byte[]> pngToJpeg(BufferedImage original) throws IOException {
        
        BufferedImage temp = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
        temp.createGraphics().drawImage(original,0,0,Color.WHITE,null);
        
        
        return this.imagemResponseDTO(temp, "jpeg");
    }

    public ResponseEntity<byte[]> jpegToPng(BufferedImage original) throws IOException {
        BufferedImage temp = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        temp.createGraphics().drawImage(original,0,0,new Color(0,0,0,0),null);
        return this.imagemResponseDTO(temp, "png");
    }
    
}
