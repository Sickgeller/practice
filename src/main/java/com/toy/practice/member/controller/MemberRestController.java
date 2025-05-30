package com.toy.practice.member.controller;

import com.toy.practice.common.exception.ErrorCode;
import com.toy.practice.common.response.ApiResponse;
import com.toy.practice.member.dto.MemberRequest;
import com.toy.practice.member.dto.MemberResponse;
import com.toy.practice.member.model.Member;
import com.toy.practice.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<MemberResponse>> register(@RequestBody MemberRequest.SignUp request) {
        Long memberId = memberService.register(
                request.getId(),
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created(
                        MemberResponse.from(memberService.findById(memberId)),
                        "회원가입이 완료되었습니다."
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<MemberResponse>> login(@RequestBody MemberRequest.Login request, HttpSession session) {
        try {
            Member member = memberService.login(request);
            MemberResponse loginMember = MemberResponse.from(member);
            session.setAttribute("loginMember", loginMember);
            return ResponseEntity.ok(ApiResponse.success(loginMember, "로그인에 성공했습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("아이디 또는 비밀번호가 올바르지 않습니다."));
        }
    }

    @GetMapping("/check-login")
    public ResponseEntity<ApiResponse<MemberResponse>> checkLogin(HttpServletRequest request) {
        MemberResponse loginMember = (MemberResponse) request.getAttribute("loginMember");
        if (loginMember != null) {
            return ResponseEntity.ok(ApiResponse.success(loginMember, "로그인 상태입니다."));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("로그인이 필요합니다."));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberResponse>>> getAllMembers() {
        List<MemberResponse> members = memberService.findAll().stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(members));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberResponse>> getMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(ApiResponse.success(
                MemberResponse.from(memberService.findById(memberId))
        ));
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberResponse>> updateMember(
            @PathVariable Long memberId,
            @RequestBody MemberRequest.Update request) {
        return ResponseEntity.ok(ApiResponse.success(
                MemberResponse.from(memberService.update(memberId, request.getName(), request.getEmail())),
                "회원 정보가 수정되었습니다."
        ));
    }

    @DeleteMapping("/delete/{memberId}")
    public ResponseEntity<ApiResponse<Void>> deleteMember(@PathVariable Long memberId) {
        memberService.delete(memberId);
        return ResponseEntity.ok(ApiResponse.ok("회원이 삭제되었습니다."));
    }

    @GetMapping("/check-email")
    public ResponseEntity<ApiResponse<Boolean>> checkEmailDuplicate(@RequestParam String email) {
        boolean isAvailable = !memberService.existsByEmail(email);
        return ResponseEntity.ok(ApiResponse.success(isAvailable));
    }

    @GetMapping("/check-id")
    public ResponseEntity<ApiResponse<Boolean>> checkIdDuplicate(@RequestParam String id) {
        boolean isAvailable = !memberService.existsById(id);
        return ResponseEntity.ok(ApiResponse.success(isAvailable));
    }
}