package com.toy.practice.board.exception;

import com.toy.practice.common.exception.BusinessException;
import com.toy.practice.common.enums.ErrorCode;

public class BoardException extends BusinessException {

    protected BoardException(ErrorCode errorCode) {
        super(errorCode);
    }

    protected BoardException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
