package com.example.t07_backend_notification_soa.exception;

public class NotFoundException extends RuntimeException {
    private static final String DESCRIPTION = "No se encontro el recuerso especificado ";

    public NotFoundException(String message) {
        super(String.format("%s : %s", DESCRIPTION, message));
    }
}
