package com.yildiz.serhat.coffeestoreservice.exception;

import com.yildiz.serhat.coffeestoreservice.controller.model.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR_LOG_FORMAT_STR = "Api Exception Occurred, exception:%s, errorCode:%s, errorMsg:%s";

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse> handleServiceExceptions(ProductNotFoundException exception) {
        log.error(String.format(ERROR_LOG_FORMAT_STR,
                exception.getClass().getName(), exception.getMessage()), exception);

        return createErrorResponse(exception.getHttpStatus(), exception.getErrorCode(), exception.getErrorMessage());
    }

    private ResponseEntity<ApiResponse> createErrorResponse(HttpStatus httpStatus, int errorCode, String errorMessage, Object body) {
        ResponseEntity<ApiResponse> errorResponse = createErrorResponse(httpStatus, errorCode, errorMessage);
        errorResponse.getBody().setOperationResultData(body);

        return errorResponse;
    }

    private ResponseEntity<ApiResponse> createErrorResponse(HttpStatus httpStatus, int errorCode, String errorMessage) {
        return ResponseEntity
                .status(httpStatus)
                .body(ApiResponse.builder()
                        .operationResult(ApiResponse.OperationResult.builder()
                                .returnCode(Integer.toString(errorCode))
                                .returnMessage(errorMessage)
                                .build())
                        .build());
    }
}
