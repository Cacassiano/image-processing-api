package dev.cacassiano.image_processing_api.exceptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;

import dev.cacassiano.image_processing_api.exceptions.custom.SupportedTypesException;
import dev.cacassiano.image_processing_api.exceptions.custom.ValidationExceptionDTO;

@RestControllerAdvice
public class GlobalExcetionHandler {

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ValidationExceptionDTO> validationExeptionHandler(HandlerMethodValidationException ex) {
        ValidationExceptionDTO response = new ValidationExceptionDTO(
            "Invalid arguments in the request",
            ex.getAllErrors()
                .stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .toList()
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(SupportedTypesException.class)
    public ResponseEntity<Map<String, String>> supportedTypesHandler(SupportedTypesException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Invalid arguments in the request");
        response.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Map<String, String>> mediaNotSupportedHandler(HttpMediaTypeNotSupportedException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        response.put("message", ex.getContentType()+" is not supported");
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(response);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Map<String, String>> ioExceptionHandler(IOException ex){
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return ResponseEntity.unprocessableEntity().body(response);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> exceptionHandler(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());

        return ResponseEntity.internalServerError().body(response);
    }
}
