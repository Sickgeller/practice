package com.toy.practice.common.exception;

import com.toy.practice.common.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.error("excepted error occurred", e);
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(ErrorResponse.of(e.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Unexpected error occurred", e);
        ErrorCode error = ErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(error.getStatus()).body(ErrorResponse.of(error));
    }
}