package com.yildiz.serhat.coffeestoreservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotFoundException extends BaseRuntimeException {
    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND.value(), "User Not Found", HttpStatus.NOT_FOUND);
    }
}


