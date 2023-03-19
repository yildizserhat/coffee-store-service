package com.yildiz.serhat.coffeestoreservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends BaseRuntimeException {

    public ProductNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND.value(), "Product Not Found", HttpStatus.NOT_FOUND);
    }
}
