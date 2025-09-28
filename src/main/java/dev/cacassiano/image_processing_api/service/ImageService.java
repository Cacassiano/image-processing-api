package dev.cacassiano.image_processing_api.service;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.springframework.stereotype.Service;

import dev.cacassiano.image_processing_api.service.interfaces.ImageToByteConversor;

@Service
public class ImageService extends ImageToByteConversor{

    // Mirror the sended image
    public byte[] mirrorImage(BufferedImage originalIOImage, String format) throws IOException {
        // Create a AffineTransform object with a -1 x scale instance
        AffineTransform transform = AffineTransform.getScaleInstance(-1, 1);
        // Translate the image all your length backwards, to when the scale negative be applied
        // the length dont be less than 0
        transform.translate(-originalIOImage.getWidth(), 0);

        // Create AffinetransformOp Object with the previus transformation
        AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        // Apply the operation to the received image, and send to a "null destination" 
        BufferedImage newImage = operation.filter(originalIOImage, null);
        
        // return the ResponseEntity with the bytes of the image and MIME type in header
        return this.imageToByteArray(newImage, format);
    }

    // Apply rescales to the image
    public byte[] rescaleImage(BufferedImage original, String format, Float scaleX, Float scaleY) throws IOException {
        
        // Create Affine transform object with the given scale values
        AffineTransform transform = AffineTransform.getScaleInstance(scaleX, scaleY);
        // Make a scale transformation
        transform.scale(transform.getScaleX(), transform.getScaleY());
        
        // Create TransformOperation object with the transforms above
        AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
        // Create the image with the new scale
        BufferedImage newImage = operation.filter(original, null);

        // Return the ResponseEntity with the image & MIME header
        return this.imageToByteArray(newImage, format);
    }

    // apply aa rotation angle () to the image 
    public byte[] rotateImage(BufferedImage original, Double inclinationInDegrees, String format) throws IOException {
        // save the original x and y image's values
        int x = original.getWidth(), y = original.getHeight();
        // Create a copy of the original image (?)
        BufferedImage temp = original;

        // if the rotation angle make the image perfectly horizontal swap the y and x 
        if (inclinationInDegrees%90 == 0 && (inclinationInDegrees/90)%2 != 0) {
            temp = new BufferedImage(y, x, temp.getType());
            temp.createGraphics().drawImage(original, null, y, x);
        }

        // Create transform Object        
        AffineTransform transform = new AffineTransform();
        // Apply a rotate transformation that starts at the center of the image 
        transform.rotate(Math.toRadians(inclinationInDegrees), temp.getWidth()/2, temp.getHeight()/2);

        // Create transformOperation with the transform settings above 
        AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        // Create a new image with the operatipon created
        BufferedImage newImage = operation.filter(original, null);
        
        // Return the ResponseEntity with image & MIME header
        return this.imageToByteArray(newImage, format);
    }
    
}
