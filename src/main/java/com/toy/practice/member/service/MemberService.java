package com.toy.practice.member.service;

import com.toy.practice.member.model.Member;
import com.toy.practice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    /**
    * 새 회원 등록
    * @param id
    * @param name
    * @param password
    * @param email
    * @return 등록된 member 고유 Id
    * */
    public Long register(String id, String name, String email, String password);

    /**
    * 회원 상세 정보 조회
    * @param memberId 조회할 회원의 Id
    * @return 조회할 회원의 엔티티
    * @throws IllegalArgumentException 존재X 회원
    * */
    public Member findById(Long memberId);

    /**
     *
     * @param memberId 조회할 대상 id
     * @param oldPassword 바꿀 이전 비밀번호
     * @param newPassword 바꿀 비밀번호
     */
    public void changePassword(Long memberId, String oldPassword, String newPassword);

    /**
     * 회원 활성화
     * @param memberId 활성화할 멤버 고유 Id
     */
    public void activate(Long memberId);

    /**
     * 회원 비활성화
     * @param memberId 활성화 하지않을 고유 id
     */
    public void deactivacte(Long memberId);

}
