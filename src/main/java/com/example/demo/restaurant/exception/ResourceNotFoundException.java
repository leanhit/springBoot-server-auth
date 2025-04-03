package com.example.demo.restaurant.exception;

import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 7992904489502842099L;

    public ResourceNotFoundException() {
        this("EntityRepresentationModel not found");
    }

    public ResourceNotFoundException(String message) {
        this(message, null);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
