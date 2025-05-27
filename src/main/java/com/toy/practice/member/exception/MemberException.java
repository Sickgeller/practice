package com.toy.practice.member.exception;

import com.toy.practice.common.exception.BusinessException;
import com.toy.practice.common.exception.ErrorCode;

public class MemberException extends BusinessException {

    public MemberException(String message, ErrorCode errorCode, String detail) {
        super(message, errorCode, detail);
    }

    public static void duplicatedMemberId(){
        ErrorCode error = ErrorCode.DUPLICATED_MEMBER_ID;
        throw new MemberException(error.getMessage(), error, error.getCode());
    }

    public static void duplicatedMemberEmail(){
        ErrorCode error = ErrorCode.DUPLICATED_MEMBER_EMAIL;
        throw new MemberException(error.getMessage(), error, error.getCode());
    }

}
