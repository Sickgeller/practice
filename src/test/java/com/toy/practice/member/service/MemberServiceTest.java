package com.toy.practice.member.service;

import com.toy.practice.member.model.Member;
import com.toy.practice.member.exception.MemberException;
import com.toy.practice.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MemberServiceTest {

    @InjectMocks
    private MemberServiceImpl memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Member member;
    private String encodedPassword;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .memberId(1L)
                .id("testId")
                .password("testPassword")
                .name("테스트")
                .email("test@test.com")
                .build();
        encodedPassword = "encodedPassword";

        // 기본 Mock 설정
        when(memberRepository.findById(anyString())).thenReturn(Optional.of(member));
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        when(memberRepository.existsById(anyString())).thenReturn(false);
        when(memberRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
    }

    @Test
    @DisplayName("회원가입 성공")
    void registerSuccess() {
        // given
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // when
        memberService.register(member);

        // then
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    @DisplayName("ID 중복으로 회원가입 실패")
    void registerFailByDuplicateId() {
        // given
        when(memberRepository.existsById(member.getId())).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> memberService.register(member))
                .isInstanceOf(MemberException.class)
                .hasMessageContaining("이미 사용중인 ID입니다.");
    }

    @Test
    @DisplayName("이메일 중복으로 회원가입 실패")
    void registerFailByDuplicateEmail() {
        // given
        when(memberRepository.existsByEmail(member.getEmail())).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> memberService.register(member))
                .isInstanceOf(MemberException.class)
                .hasMessageContaining("이미 사용중인 이메일입니다.");
    }

    @Test
    @DisplayName("회원 정보 수정 성공")
    void updateSuccess() {
        // given
        Member updateMember = Member.builder()
                .memberId(1L)
                .name("수정된이름")
                .email("updated@test.com")
                .build();
        when(memberRepository.findById(updateMember.getMemberId())).thenReturn(Optional.of(member));

        // when
        Member updatedMember = memberService.update(updateMember);

        // then
        assertThat(updatedMember.getName()).isEqualTo(updateMember.getName());
        assertThat(updatedMember.getEmail()).isEqualTo(updateMember.getEmail());
    }

    @Test
    @DisplayName("비밀번호 변경 성공")
    void changePasswordSuccess() {
        // given
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        when(memberRepository.findById(member.getMemberId())).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(oldPassword, member.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedNewPassword");

        // when
        memberService.changePassword(member.getMemberId(), oldPassword, newPassword);

        // then
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    @DisplayName("회원 활성화 성공")
    void activateSuccess() {
        // given
        when(memberRepository.findById(member.getMemberId())).thenReturn(Optional.of(member));

        // when
        memberService.activate(member.getMemberId());

        // then
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    @DisplayName("회원 비활성화 성공")
    void deactivateSuccess() {
        // given
        when(memberRepository.findById(member.getMemberId())).thenReturn(Optional.of(member));

        // when
        memberService.deactivacte(member.getMemberId());

        // then
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    @DisplayName("회원 삭제 성공")
    void deleteSuccess() {
        // given
        when(memberRepository.findById(any(Long.class))).thenReturn(Optional.of(member));
        doNothing().when(memberRepository).delete(any(Member.class));

        // when
        memberService.delete(any(Long.class));

        // then
        verify(memberRepository).delete(any(Member.class));
    }

    @Test
    @DisplayName("String 타입 ID로 회원 존재 여부 확인")
    void existsByIdWithString() {
        // given
        when(memberRepository.existsById(member.getId())).thenReturn(true);

        // when
        boolean exists = memberService.findById(member.getId()) != null;

        // then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Long 타입 ID로 회원 존재 여부 확인")
    void existsByIdWithLong() {
        // given
        when(memberRepository.existsById(member.getMemberId())).thenReturn(true);

        // when
        boolean exists = memberService.existsById(member.getMemberId());

        // then
        assertThat(exists).isTrue();
    }
} 