package com.toy.practice.member.exception;

import com.toy.practice.common.exception.BusinessException;
import com.toy.practice.common.exception.ErrorCode;

public class MemberException extends BusinessException {

    private MemberException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static MemberException duplicatedMemberId() {
        return new MemberException(ErrorCode.DUPLICATED_MEMBER_ID);
    }

    public static MemberException duplicatedMemberEmail() {
        return new MemberException(ErrorCode.DUPLICATED_MEMBER_EMAIL);
    }

    public static MemberException loginFailed() {
        return new MemberException(ErrorCode.LOGIN_FAILED);
    }

    public static MemberException memberNotFound() {
        return new MemberException(ErrorCode.MEMBER_NOT_FOUND);
    }
}
