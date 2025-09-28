package dev.cacassiano.image_processing_api.service;

public enum SupportedTypes {
    PNG("png"),
    JPEG("jpeg");

    private String value;

    SupportedTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
