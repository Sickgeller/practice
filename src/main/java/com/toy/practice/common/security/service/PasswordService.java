package com.toy.practice.common.security.service;

public interface PasswordService {
    /**
     * 비밀번호 암호화
     * @param rawPassword 암호화 되지않은 비밀번호
     * @return 암호화 진행된 비밀번호
     */
    String encode(String rawPassword);

    /**
     * 비밀번호 검증
     * @param rawPassword 암호화 안된 비밀번호
     * @param encodedPassword 암호화 된 비밀번호
     * @return 일치한지 확인
     */
    boolean matches(String rawPassword, String encodedPassword);

    /**
     * 유효성 검사 (형식에 맞는지 등등)
     * @param rawPassword 유효성 검사할 비밀번호
     */
    void validate(String rawPassword);

}
