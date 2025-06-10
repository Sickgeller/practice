package com.toy.practice.member.appservice;

import com.toy.practice.member.dto.MemberLoginRequest;
import com.toy.practice.member.dto.MemberResponse;
import com.toy.practice.member.dto.MemberSignUpRequest;
import com.toy.practice.member.dto.MemberUpdateRequest;
import com.toy.practice.member.exception.MemberException;
import com.toy.practice.member.mapper.MemberMapper;
import com.toy.practice.member.model.Member;
import com.toy.practice.member.repository.MemberRepository;
import com.toy.practice.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberAppServiceTest {

    @Autowired
    private MemberAppService memberAppService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMapper memberMapper;

    private Member member;
    private MemberSignUpRequest signUpRequest;
    private MemberLoginRequest loginRequest;
    private MemberUpdateRequest updateRequest;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();

        member = Member.builder()
                .id("testId")
                .password("testPassword")
                .name("테스트")
                .email("test@test.com")
                .build();

        signUpRequest = new MemberSignUpRequest(
                "testId",
                "testPassword",
                "테스트",
                "test@test.com"
        );

        loginRequest = new MemberLoginRequest(
                "testId",
                "testPassword"
        );

        updateRequest = new MemberUpdateRequest(
                "수정된이름",
                "updated@test.com"
        );
    }

    @Test
    @DisplayName("회원가입 후 로그인 성공")
    void signUpAndLoginSuccess() {
        // given
        memberAppService.register(signUpRequest);

        // when
        MemberResponse loginMember = memberAppService.login(loginRequest);

        // then
        assertThat(loginMember).isNotNull();
        assertThat(loginMember.getId()).isEqualTo(signUpRequest.getId());
    }

    @Test
    @DisplayName("잘못된 비밀번호로 로그인 실패")
    void loginFailByWrongPassword() {
        // given
        memberAppService.register(signUpRequest);
        MemberLoginRequest wrongPasswordRequest = new MemberLoginRequest(
                "testId",
                "wrongPassword"
        );

        // when & then
        assertThatThrownBy(() -> memberAppService.login(wrongPasswordRequest))
                .isInstanceOf(MemberException.class)
                .hasMessageContaining("아이디 또는 비밀번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("존재하지 않는 로그인 ID로 로그인 실패")
    void loginFailByNonExistentLoginId() {
        // given
        MemberLoginRequest nonExistentRequest = new MemberLoginRequest(
                "nonExistentId",
                "testPassword"
        );

        // when & then
        assertThatThrownBy(() -> memberAppService.login(nonExistentRequest))
                .isInstanceOf(MemberException.class)
                .hasMessageContaining("아이디 또는 비밀번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("회원 목록 조회")
    void findAllMembers() {
        // given
        memberAppService.register(signUpRequest);
        memberAppService.register(new MemberSignUpRequest(
                "testId2",
                "testPassword2",
                "테스트2",
                "test2@test.com"
        ));

        // when
        List<MemberResponse> members = memberAppService.getAllMembers();

        // then
        assertThat(members).hasSize(2);
    }

    @Test
    @DisplayName("존재하지 않는 회원 조회")
    void findMemberByIdFail() {
        // when & then
        assertThatThrownBy(() -> memberAppService.findById("nonExistentId"))
                .isInstanceOf(MemberException.class)
                .hasMessageContaining("존재하지 않는 회원입니다.");
    }

    @Test
    @DisplayName("회원 상세 정보 조회")
    void findMemberById() {
        // given
        memberAppService.register(signUpRequest);

        // when
        MemberResponse foundMember = memberAppService.findById(signUpRequest.getId());

        // then
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getId()).isEqualTo(signUpRequest.getId());
        assertThat(foundMember.getName()).isEqualTo(signUpRequest.getName());
        assertThat(foundMember.getEmail()).isEqualTo(signUpRequest.getEmail());
    }
} 