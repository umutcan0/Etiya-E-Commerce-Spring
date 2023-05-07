package com.etiya.ecommerce.core.exception.types;

public class ServiceException extends RuntimeException {
    public ServiceException(String message){
        super(message);
    }
}
