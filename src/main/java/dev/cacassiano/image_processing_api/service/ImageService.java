package dev.cacassiano.image_processing_api.service;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

@Service
public class ImageService {

    public byte[] mirrorImage(BufferedImage originalIOImage) throws IOException {
        AffineTransform transform = AffineTransform.getScaleInstance(-1, 1);
        transform.translate(-originalIOImage.getWidth(), 0);

        AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage newImage = operation.filter(originalIOImage, null);

        ByteArrayOutputStream armazenador = new ByteArrayOutputStream();
        ImageIO.write(newImage, "png", armazenador);
        return armazenador.toByteArray();
         
    }
    
}
