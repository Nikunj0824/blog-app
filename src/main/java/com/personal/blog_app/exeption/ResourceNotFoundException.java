package com.personal.blog_app.exeption;

public class ResourceNotFoundException extends RuntimeException {

    public <T> ResourceNotFoundException(String resourceName, String fieldName, T fieldValue) {
        super(String.format("%s with %s: %s not found", resourceName, fieldName, fieldValue));
    }
}
