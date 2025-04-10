package dev.cacassiano.image_processing_api.service;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    public ResponseEntity<byte[]> mirrorImage(BufferedImage originalIOImage, String format) throws IOException {
        
        AffineTransform transform = AffineTransform.getScaleInstance(-1, 1);
        transform.translate(-originalIOImage.getWidth(), 0);

        AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage newImage = operation.filter(originalIOImage, null);

        ByteArrayOutputStream storage = new ByteArrayOutputStream();
        if(format.equals("png")){

            boolean create = ImageIO.write(newImage, "png", storage);
            if (create) {
                return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(storage.toByteArray());
            } else {
                return ResponseEntity.unprocessableEntity().build();
            }

        } else if(format.equals("jpeg")){

            boolean create = ImageIO.write(newImage, "jpeg", storage);
            if (create) {
                return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(storage.toByteArray());
            } else {
                return ResponseEntity.unprocessableEntity().build();
            }

        } else {
            return ResponseEntity.internalServerError()
                .body(storage.toByteArray());
        }
         
    }
    
}
