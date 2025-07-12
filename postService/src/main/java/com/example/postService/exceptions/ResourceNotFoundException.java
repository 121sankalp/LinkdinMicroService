package com.example.postService.exceptions;

public class ResourceNotFoundException extends  RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
