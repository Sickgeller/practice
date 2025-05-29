package com.toy.practice.common.exception;

import lombok.*;


@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private int status;
    private String errorCode;
    private String message;

    public ErrorResponse(final ErrorCode errorCode) {
        this.status = errorCode.getStatus().value();
        this.errorCode = errorCode.getCode();
        this.message = errorCode.getMessage();
    }


    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .errorCode(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }

}
