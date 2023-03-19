package com.yildiz.serhat.coffeestoreservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsException extends BaseRuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message, HttpStatus.BAD_REQUEST.value(), "User Already Exist", HttpStatus.BAD_REQUEST);
    }
}


