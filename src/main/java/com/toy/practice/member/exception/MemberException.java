package com.toy.practice.member.exception;

import com.toy.practice.common.exception.BusinessException;
import com.toy.practice.common.exception.ErrorCode;

public class MemberException extends BusinessException {
    private MemberException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }

    private MemberException(ErrorCode errorCode, String detail, Throwable cause) {
        super(errorCode, detail, cause);
    }

    public static MemberException duplicatedMemberId() {
        return new MemberException(
            ErrorCode.DUPLICATED_MEMBER_ID,
            "Member ID already exists"
        );
    }

    public static MemberException duplicatedMemberEmail() {
        return new MemberException(
            ErrorCode.DUPLICATED_MEMBER_EMAIL,
            "Member email already exists"
        );
    }

    public static MemberException loginFailed() {
        return new MemberException(
            ErrorCode.LOGIN_FAILED,
            "Invalid login credentials"
        );
    }

    public static MemberException memberNotFound(Long memberId) {
        return new MemberException(
            ErrorCode.MEMBER_NOT_FOUND,
            String.format("Member not found with id: %d", memberId)
        );
    }
}
