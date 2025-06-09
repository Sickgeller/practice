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

            getOk(redirectAttributes, "회원 정보가 수정되었습니다.");
            log.info("회원 정보 수정 성공");
            return "redirect:/members/detail";
        } catch (Exception e) {
            log.error("회원 정보 수정 실패 - 에러: {}", e.getMessage());
            getInternalException(e);
            return "redirect:/members/edit";
        }
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
