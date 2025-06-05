package com.toy.practice.member.controller;

import com.toy.practice.common.response.ApiResponse;
import com.toy.practice.member.appservice.MemberAppService;
import com.toy.practice.member.dto.MemberLoginRequest;
import com.toy.practice.member.dto.MemberResponse;
import com.toy.practice.member.dto.MemberSignUpRequest;
import com.toy.practice.member.exception.MemberException;
import com.toy.practice.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    private final MemberAppService memberAppService;

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

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<MemberResponse>> login(@RequestBody MemberLoginRequest request, HttpSession session) {
        log.info("REST 로그인 요청 - ID: {}", request.getId());
        try {
            MemberResponse member = memberAppService.login(request);
            sessionSetUp(session, member);
            log.info("REST 로그인 성공 - ID: {}", request.getId());
            return getOk(member, "로그인에 성공했습니다.");
        } catch (MemberException e) {
            log.error("REST 로그인 실패 - ID: {}, 에러: {}", request.getId(), e.getMessage());
            return getMemberException(e);
        } catch (Exception e) {
            log.error("REST 로그인 실패 - ID: {}, 에러: {}", request.getId(), e.getMessage());
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

    @DeleteMapping("/delete/{memberId}")
    public ResponseEntity<ApiResponse<Void>> deleteMember(@PathVariable Long memberId, HttpSession session) {
        log.info("REST 회원 삭제 요청 - memberId: {}", memberId);
        try {
            memberService.delete(memberId);
            session.removeAttribute("loginMember");
            log.info("REST 회원 삭제 성공 - memberId: {}", memberId);
            return getOk(null, "회원이 삭제되었습니다.");
        } catch (MemberException e) {
            log.error("REST 회원 삭제 실패 - memberId: {}, 에러: {}", memberId, e.getMessage());
            return getMemberException(e);
        } catch (Exception e) {
            log.error("REST 회원 삭제 실패 - memberId: {}, 에러: {}", memberId, e.getMessage());
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
        log.error("MemberException 발생: {}", e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(ApiResponse.error(e.getMessage()));
    }

    private <T> ResponseEntity<ApiResponse<T>> getInternalException(Exception e) {
        log.error("내부 서버 에러 발생: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(e.getMessage()));
    }
}
