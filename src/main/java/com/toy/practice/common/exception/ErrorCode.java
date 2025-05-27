package com.toy.practice.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 공통
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "잘못된 입력값입니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S001" , "서버오류입니다." ),

    //인증, 인가
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A001", "인증에 실패했습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "A002" , "접근 권한이 없습니다."),

    //Member 관련
    DUPLICATED_MEMBER_ID(HttpStatus.CONFLICT, "M001", "이미 사용중인 ID입니다."),
    DUPLICATED_MEMBER_EMAIL(HttpStatus.CONFLICT, "M002" , "이미 사용중인 이메일입니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;
}
