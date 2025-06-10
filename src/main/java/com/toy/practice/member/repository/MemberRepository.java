package com.toy.practice.member.repository;

import com.toy.practice.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    /**
     * 고유식별 Id가 아니라 텍스트 Id로 멤버가 조회하는지 보는 메서드 (중복)
     * @param id 존재하는지 확인할 id
     * @return 존재여부 반환
     */
    boolean existsById(String id);

    /**
     * 고유식별 Id가 아니라 이메일로 멤버가 조회하는지 보는 메서드 (중복)
     * @param email 존재하는지 확인할 이메일
     * @return 존재여부 반환
     */
    boolean existsByEmail(String email);

    /**
     * 순수 멤버 Id로 검색
     * @param memberId 검색할 memberId
     * @return 검색된 멤버
     */
    Optional<Member> findByMemberId(Long memberId);

    /**
     * 그냥 Id로 멤버 검색
     *
     * @param id 존재하는지 확인할 아이디
     * @return 검색된 멤버
     */
    Optional<Member> findById(String id);

    /**
     * 이메일로 유저검색
     * @param email 존재하는지 확인할 이메일
     * @return 검색된 멤버
     */
    Optional<Member> findByEmail(String email);

}
