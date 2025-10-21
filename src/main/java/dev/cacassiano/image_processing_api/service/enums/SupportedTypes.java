package dev.cacassiano.image_processing_api.service.enums;

public enum SupportedTypes {
    PNG("png"),
    JPEG("jpeg");

    private final String value;

    SupportedTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
