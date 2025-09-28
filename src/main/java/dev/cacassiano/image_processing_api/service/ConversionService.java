package dev.cacassiano.image_processing_api.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dev.cacassiano.image_processing_api.service.interfaces.ImageToByteConversor;

@Service
public class ConversionService extends ImageToByteConversor{

    // TODO refact para aceitar mais tipos de image como gif e etc
    public byte[] convert(MultipartFile image, String destFormat) throws IOException {
        if (destFormat.equals("jpeg")) {
            return pngToJpeg(ImageIO.read(image.getInputStream()));
        }
        return jpegToPng(ImageIO.read(image.getInputStream()));
    }

    private byte[] pngToJpeg(BufferedImage original) throws IOException {
        BufferedImage temp = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
        temp.createGraphics().drawImage(original,0,0,Color.WHITE,null);
        return this.imageToByteArray(temp, "jpeg");
    }

    private byte[] jpegToPng(BufferedImage original) throws IOException {
        BufferedImage temp = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        temp.createGraphics().drawImage(original,0,0,new Color(0,0,0,0),null);
        return this.imageToByteArray(temp, "jpeg");
    }
}