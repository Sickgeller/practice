package com.toy.practice.member.service;

import com.toy.practice.member.exception.MemberException;
import com.toy.practice.member.model.Member;

import java.util.List;

public interface MemberService {

    /**
    * 새 회원 등록
    * @param member 등록할 회원
    * */
    void register(Member member);

//    /**
//     * 로그인
//     *
//     * @param member 로그인할 정보가 담긴 member

    /**
     * 모든 회원 조회
     * @return 모든 회원 목록
     */
    List<Member> findAll();

    /**
    * 회원 상세 정보 조회
    * @param memberId 조회할 회원의 Id
    * @return 조회할 회원의 엔티티
    * @throws MemberException 존재X 회원
    * */
    Member findById(Long memberId);

    /**
    * 회원 상세 정보 조회
    * @param id 로그인용 id
    * @return 조회할 회원의 엔티티
    * @throws MemberException 존재X 회원
    * */
    Member findById(String id);

    /**
     *  이메일로 검색
     * @param email
     * @return 조회한 회원의 엔티티
     * @throws MemberException 존재X 회원
     */
    Member findByEmail(String email);


    /**
     * 회원 정보 수정
     * @param member 수정할 정보가 담긴 도메인
     * @return 수정된 회원 엔티티
     */
    Member update(Member member);

    /**
     * 회원 삭제
     * @param memberId 삭제할 회원의 Id
     */
    void delete(Long memberId);

    /**
     *
     * @param memberId 조회할 대상 id
     * @param oldPassword 바꿀 이전 비밀번호
     * @param newPassword 바꿀 비밀번호
     */
    void changePassword(Long memberId, String oldPassword, String newPassword);

    /**
     * 회원 활성화
     * @param memberId 활성화할 멤버 고유 Id
     */
    void activate(Long memberId);

    /**
     * 회원 비활성화
     * @param memberId 활성화 하지않을 고유 id
     */
    void deactivacte(Long memberId);

    /**
     * 이메일 중복 체크
     * @param email 체크할 이메일
     * @return 이메일이 이미 존재하면 true, 아니면 false
     */
    boolean existsByEmail(String email);

    /**
     * 아이디 중복 체크
     * @param id 체크할 아이디
     * @return 아이디가 이미 존재하면 true, 아니면 false
     */
    boolean existsById(String id);

    /**
     * 아이디 중복 체크
     * @param memberId 체크할 아이디
     * @return 아이디가 이미 존재하면 true, 아니면 false
     */
    boolean existsById(Long memberId);
}
