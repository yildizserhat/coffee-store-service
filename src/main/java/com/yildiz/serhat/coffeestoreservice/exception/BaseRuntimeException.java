package com.yildiz.serhat.coffeestoreservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseRuntimeException extends RuntimeException {

    private final int errorCode;
    private final String errorMessage;
    private final HttpStatus httpStatus;

    public BaseRuntimeException(String message, int errorCode, String errorMessage, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
