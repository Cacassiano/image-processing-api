package dev.cacassiano.image_processing_api.service;

import java.awt.image.BufferedImage;

public enum ImageTypes {
    JPEG(BufferedImage.TYPE_INT_RGB),
    PNG(BufferedImage.TYPE_4BYTE_ABGR);

    private final int value;

    public int getValue() {
        return this.value;
    }

    private ImageTypes(int value) {
        this.value = value;
    }

    
}
