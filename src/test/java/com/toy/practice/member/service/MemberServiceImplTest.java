package com.toy.practice.member.service;

import com.toy.practice.common.exception.ErrorCode;
import com.toy.practice.member.dto.MemberRequest;
import com.toy.practice.member.exception.MemberException;
import com.toy.practice.member.model.Member;
import com.toy.practice.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private MemberServiceImpl memberService;

    private Member member = Member.builder()
            .memberId(1L)  // 테스트용 ID 설정
            .id("testId")
            .name("testName")
            .email("test@email.com")
            .password("encodedPassword")
            .build();

    @Test
    @DisplayName("Register - Success")
    void register_Success() {
        // given
        Member member = Member.builder()
                .id("testId")
                .name("testName")
                .email("test@email.com")
                .password("testPassword123!")
                .build();

        // 저장된 후 반환될 Member 객체 (memberId가 설정된 상태)
        Member savedMember = Member.builder()
                .memberId(1L)  // 테스트용 ID 설정
                .id("testId")
                .name("testName")
                .email("test@email.com")
                .password("encodedPassword")
                .build();

        // when
        when(memberRepository.existsById("testId")).thenReturn(false);
        when(memberRepository.existsByEmail("test@email.com")).thenReturn(false);
        when(memberRepository.save(any(Member.class))).thenReturn(savedMember);  // 저장된 객체 반환

        // 테스트할 메서드 호출
        Long memberId = memberService.register(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getPassword()
        );

        // then
        assertThat(memberId).isEqualTo(1L);  // 예상되는 memberId 확인
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    @DisplayName("register failure - duplicated id")
    void register_failIdDuplicate() {
        // given
        String duplicateId = "duplicateId";
        String name1 = "testName1";
        String email1 = "test1@email.com";
        String password1 = "testPassword123!";

        String name2 = "testName2";
        String email2 = "test2@email.com";
        String password2 = "testPassword456!";

        // 첫 번째 회원 저장 성공
        Member firstMember = Member.builder()
                .id(duplicateId)
                .name(name1)
                .email(email1)
                .password(password1)
                .build();

        // 두 번째 회원 (동일한 ID 사용)
        Member duplicateMember = Member.builder()
                .id(duplicateId)  // 동일한 ID
                .name(name2)
                .email(email2)
                .password(password2)
                .build();

        // 첫 번째 회원 저장 시도는 성공
        when(memberRepository.existsById(duplicateId))
                .thenReturn(false)  // 첫 번째 호출: false 반환 (중복 아님)
                .thenReturn(true);  // 두 번째 호출: true 반환 (중복됨)

        when(memberRepository.existsByEmail(email1)).thenReturn(false);

        // 첫 번째 회원 저장 성공
        when(memberRepository.save(any(Member.class)))
                .thenAnswer(invocation -> {
                Member m = invocation.getArgument(0);
                return Member.builder()
                        .memberId(1L)
                        .id(m.getId())
                        .name(m.getName())
                        .email(m.getEmail())
                        .password(m.getPassword())
                        .build();
                });

        // when & then
        memberService.register(duplicateId, name1, email1, password1);

        assertThatThrownBy(() ->
                memberService.register(duplicateId, name2, email2, password2)
        ).isInstanceOf(MemberException.class)
        .hasMessageContaining("이미 사용중인 ID입니다");

        // verify
        verify(memberRepository, times(2)).existsById(duplicateId);
        verify(memberRepository, times(1)).existsByEmail(email1);
        verify(memberRepository, times(0)).existsByEmail(email2);
        verify(memberRepository, times(1)).save(any(Member.class));  // 첫 번째 저장만 호출됨
    }

    @Test
    void findById() {
        //given
        memberRepository.save(member);
        Member expectedMember = Member.builder()
                .memberId(1L)  // 테스트용 ID 설정
                .id("testId")
                .name("testName")
                .email("test@email.com")
                .password("encodedPassword")
                .build();
        when(memberRepository.findById("testId")).thenReturn(Optional.of(expectedMember));
        //when
        Member foundMember = memberRepository.findById("testId").get();

        //then
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getMemberId()).isEqualTo(1L);
        assertThat(foundMember.getId()).isEqualTo("testId");

        verify(memberRepository, times(1)).findById("testId");
    }

    @Test
    void changePassword() {
        // given
        // 1. 테스트용 회원 생성
        Member member = Member.builder()
                .memberId(1L)
                .id("testUser")
                .name("홍길동")
                .email("test@example.com")
                .password("oldEncodedPassword")
                .build();

        String newRawPassword = "newPassword123!";

        // when
        member.changePassword(newRawPassword);

        // then
        // 3. 비밀번호가 올바르게 변경되었는지 검증
        assertThat(member.getPassword()).isEqualTo(newRawPassword);

        // 4. 저장 로직이 호출되었는지 검증 (선택사항)
        // 만약 changePassword 내부에서 저장 로직을 호출한다면
        // verify(memberRepository, times(1)).save(member);
    }

    @Test
    void activate() {
    }

    @Test
    void deactivacte() {
    }
}