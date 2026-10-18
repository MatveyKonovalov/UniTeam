package ru.uniteam.webplatform.controllers.opened.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.uniteam.models.exceptions.PermissionException;
import ru.uniteam.webplatform.data.entities.dto.security.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleNotFound(IllegalArgumentException e) {
        return ResponseEntity.status(404).body(new ApiResponse(e.getMessage()));
    }

    @ExceptionHandler(PermissionException.class)
    public ResponseEntity<ApiResponse> handleForbidden(PermissionException e) {
        return ResponseEntity.status(403).body(new ApiResponse(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleAll(Exception e) {
        return ResponseEntity.status(500).body(new ApiResponse("Internal server error"));
    }
}