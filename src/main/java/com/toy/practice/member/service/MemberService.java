package com.toy.practice.member.service;

import com.toy.practice.member.exception.MemberException;
import com.toy.practice.member.model.Member;
import com.toy.practice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * 로그인
     * @param id 로그인할 아이디
     * @param password 비밀번호
     * @return 로그인한 회원 엔티티
     * @throws MemberException 로그인 실패 시
     */
    public Member login(String id, String password);

    /**
     * 모든 회원 조회
     * @return 모든 회원 목록
     */
    public List<Member> findAll();

    /**
    * 회원 상세 정보 조회
    * @param memberId 조회할 회원의 Id
    * @return 조회할 회원의 엔티티
    * @throws IllegalArgumentException 존재X 회원
    * */
    public Member findById(Long memberId);

    /**
     * 회원 정보 수정
     * @param memberId 수정할 회원의 Id
     * @param name 새로운 이름
     * @param email 새로운 이메일
     * @return 수정된 회원 엔티티
     */
    public Member update(Long memberId, String name, String email);

    /**
     * 회원 삭제
     * @param memberId 삭제할 회원의 Id
     */
    public void delete(Long memberId);

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
