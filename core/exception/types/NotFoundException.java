package com.etiya.ecommerce.core.exception.types;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
