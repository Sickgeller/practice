package com.toy.practice.common.security.exception;

import com.toy.practice.common.exception.BusinessException;
import com.toy.practice.common.enums.ErrorCode;

public class SecurityException extends BusinessException {

    protected SecurityException(ErrorCode errorCode) {
        super(errorCode);
    }
    protected SecurityException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

}
