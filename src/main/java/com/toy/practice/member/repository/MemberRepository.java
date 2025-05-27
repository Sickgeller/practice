package com.toy.practice.member.repository;

import com.toy.practice.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    /**
     * 고유식별 Id가 아니라 텍스트 Id로 멤버가 조회하는지 보는 메서드 (중복)
     * @param id
     * @return
     */
    boolean existsById(String id);

    /**
     * 고유식별 Id가 아니라 이메일로 멤버가 조회하는지 보는 메서드 (중복)
     * @param email
     * @return
     */
    boolean existsByEmail(String email);
}
