package dev.cacassiano.image_processing_api.service;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import org.springframework.stereotype.Service;

@Service
public class ImageService {

    public Byte[] mirrorImage(BufferedImage originalIOImage) {
       AffineTransform transform = AffineTransform.getScaleInstance(-1, 1);
       transform.translate(-originalIOImage.getWidth(), 0);

       AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
       BufferedImage novaImagem = operation.filter(originalIOImage, null);
    
       return null;
    }
    
}
