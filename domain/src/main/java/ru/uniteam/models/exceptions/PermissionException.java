package ru.uniteam.models.exceptions;

public class PermissionException extends RuntimeException {
    public PermissionException() {
        super("Not enough rights");
    }

    public PermissionException(String message) {
        super(message);
    }
}
