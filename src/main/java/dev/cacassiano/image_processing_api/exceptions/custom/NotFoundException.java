package dev.cacassiano.image_processing_api.exceptions.custom;

public class NotFoundException extends Exception{
    private final String message;
    public NotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
