package com.toy.practice.member.controller;

import com.toy.practice.common.response.ApiResponse;
import com.toy.practice.member.appservice.MemberAppService;
import com.toy.practice.member.dto.*;
import com.toy.practice.member.exception.MemberException;
import com.toy.practice.member.mapper.MemberMapper;
import com.toy.practice.member.model.Member;
import com.toy.practice.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberAppService memberAppService;
    private final AuthenticationManager authenticationManager;
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<MemberResponse>> register(@ModelAttribute MemberSignUpRequest request, RedirectAttributes redirectAttributes) {
        log.info("REST 회원가입 요청 - ID: {}", request.getId());
        try {
            memberAppService.register(request);
            log.info("REST 회원가입 성공 - ID: {}", request.getId());
            return getOk(null, "회원가입이 완료되었습니다.");
        } catch (MemberException e) {
            log.error("REST 회원가입 실패 - ID: {}, 에러: {}", request.getId(), e.getMessage());
            return getMemberException(e);
        } catch (Exception e) {
            log.error("REST 회원가입 실패 - ID: {}, 에러: {}", request.getId(), e.getMessage());
            return getInternalException(e);
        }
    }
    // 회원 수정 처리
    @PostMapping("/edit")
    public String edit(@ModelAttribute MemberUpdateRequest request, RedirectAttributes redirectAttributes, HttpSession session) {
        log.info("회원 정보 수정 요청");
        try {
            MemberResponse memberResponse = memberAppService.update(request);
            sessionSetUp(session, memberResponse);
            getOk(redirectAttributes, "회원 정보가 수정되었습니다.");
            log.info("회원 정보 수정 성공");
            return "redirect:/members/detail";
        } catch (Exception e) {
            log.error("회원 정보 수정 실패 - 에러: {}", e.getMessage());
            getInternalException(e);
            return "redirect:/members/edit";
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<MemberResponse>> login(@RequestBody MemberLoginRequest request, HttpSession session) {
        log.info("REST 로그인 요청 - ID: {}", request.getId());
        try {
            // Spring Security 인증 처리
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getId(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // 인증된 사용자 정보 조회
            Member member = memberService.findById(request.getId());
            MemberResponse memberResponse = memberMapper.toDto(member);
            
            // 세션에 사용자 정보 저장
            session.setAttribute("loginMember", memberResponse);
            
            log.info("REST 로그인 성공 - ID: {}", request.getId());
            return getOk(memberResponse, "로그인에 성공했습니다.");
        } catch (BadCredentialsException e) {
            log.error("REST 로그인 실패 - 잘못된 인증 정보: {}", e.getMessage());
            return getBadRequest("아이디 또는 비밀번호가 일치하지 않습니다.");
        } catch (UsernameNotFoundException e) {
            log.error("REST 로그인 실패 - 존재하지 않는 사용자: {}", e.getMessage());
            return getBadRequest("존재하지 않는 사용자입니다.");
        } catch (Exception e) {
            log.error("REST 로그인 실패 - 예상치 못한 오류: {}", e.getMessage());
            return getInternalException(e);
        }
    }

    @GetMapping("/check-login")
    public ResponseEntity<ApiResponse<MemberResponse>> checkLogin(HttpServletRequest request) {
        log.debug("REST 로그인 상태 확인 요청");
        MemberResponse loginMember = (MemberResponse) request.getAttribute("loginMember");
        if (loginMember != null) {
            log.debug("로그인 상태 확인 - ID: {}", loginMember.getId());
            return getOk(loginMember, "로그인 상태입니다.");
        }
        log.debug("로그인 상태 아님");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("로그인이 필요합니다."));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMember(@ModelAttribute MemberDeleteRequest request, HttpSession session) {
        log.info("REST 회원 삭제 요청 - memberId: {}", request.getId());
        try {
            memberAppService.deleteMember(request);
            session.removeAttribute("loginMember");
            log.info("REST 회원 삭제 성공 - memberId: {}", request.getId());
            return getOk(null, "회원이 삭제되었습니다.");
        } catch (MemberException e) {
            log.error("REST 회원 삭제 실패 - memberId: {}, 에러: {}", request.getId(), e.getMessage());
            return getMemberException(e);
        } catch (Exception e) {
            log.error("REST 회원 삭제 실패 - memberId: {}, 에러: {}", request.getId(), e.getMessage());
            return getInternalException(e);
        }
    }

    private void sessionSetUp(HttpSession session, MemberResponse member) {
        log.debug("세션 설정 - memberId: {}", member.getMemberId());
        session.setAttribute("loginMember", member);
    }

    private <T> ResponseEntity<ApiResponse<T>> getOk(T data, String message) {
        log.debug("성공 응답 생성 - 메시지: {}", message);
        return ResponseEntity.ok(ApiResponse.success(data, message));
    }

    private <T> ResponseEntity<ApiResponse<T>> getMemberException(MemberException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(e.getMessage()));
    }

    private <T> ResponseEntity<ApiResponse<T>> getInternalException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("서버 내부 오류가 발생했습니다."));
    }

    private <T> ResponseEntity<ApiResponse<T>> getBadRequest(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(message));
    }
}
