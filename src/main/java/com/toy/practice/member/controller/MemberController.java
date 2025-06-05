package com.toy.practice.member.controller;

import com.toy.practice.member.appservice.MemberAppService;
import com.toy.practice.member.dto.MemberResponse;
import com.toy.practice.member.dto.MemberSignUpRequest;
import com.toy.practice.member.dto.MemberUpdateRequest;
import com.toy.practice.member.exception.MemberException;
import com.toy.practice.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberAppService memberAppService;

    @GetMapping("/")
    public String index(){
        log.info("메인 페이지 요청");
        return "index";
    }

    // 회원 목록
    @GetMapping
    public String list(Model model) {
        log.info("회원 목록 페이지 요청");
        List<MemberResponse> members = memberAppService.getAllMembers();
        model.addAttribute("members", members);
        log.debug("조회된 회원 수: {}", members.size());
        return "members/list";
    }

    // 회원가입 폼
    @GetMapping("/register")
    public String registerForm() {
        log.info("회원가입 폼 요청");
        return "members/register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@ModelAttribute MemberSignUpRequest request, RedirectAttributes redirectAttributes) {
        log.info("회원가입 요청 - ID: {}", request.getId());
        try {
            memberAppService.register(request);
            handleMessage(redirectAttributes, "회원가입이 완료되었습니다.");
            log.info("회원가입 성공 - ID: {}", request.getId());
            return "redirect:/members/login";
        } catch (Exception e) {
            log.error("회원가입 실패 - ID: {}, 에러: {}", request.getId(), e.getMessage());
            handleError(redirectAttributes, e);
            return "redirect:/members/register";
        }
    }

    // 로그인 폼
    @GetMapping("/login")
    public String loginForm() {
        log.info("로그인 폼 요청");
        return "members/login";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        log.info("로그아웃 요청");
        session.invalidate();
        return "redirect:/";
    }

    // 회원 상세보기
    @GetMapping("/detail")
    public String detail() {
        log.info("회원 상세 페이지 요청");
        return "members/detail";
    }

    // 회원 수정 폼
    @GetMapping("/edit")
    public String editForm() {
        log.info("회원 수정 폼 요청");
        return "members/edit";
    }

    // 회원 수정 처리
    @PostMapping("/edit")
    public String edit(@ModelAttribute MemberUpdateRequest request, RedirectAttributes redirectAttributes, HttpSession session) {
        log.info("회원 정보 수정 요청");
        try {
            MemberResponse memberResponse = memberAppService.update(request);
            sessionUpdate(session, memberResponse);
            handleMessage(redirectAttributes, "회원 정보가 수정되었습니다.");
            log.info("회원 정보 수정 성공");
            return "redirect:/members/detail";
        } catch (Exception e) {
            log.error("회원 정보 수정 실패 - 에러: {}", e.getMessage());
            handleError(redirectAttributes, e);
            return "redirect:/members/edit";
        }
    }


    // 회원 삭제
    @PostMapping("/delete")
    public String delete(@RequestParam Long memberId, RedirectAttributes redirectAttributes) {
        log.info("회원 삭제 요청 - memberId: {}", memberId);
        try {
            memberService.delete(memberId);
            handleMessage(redirectAttributes, "회원이 삭제되었습니다.");
            log.info("회원 삭제 성공 - memberId: {}", memberId);
        } catch (Exception e) {
            log.error("회원 삭제 실패 - memberId: {}, 에러: {}", memberId, e.getMessage());
            handleError(redirectAttributes, e);
        }
        return "redirect:/members/list";
    }

    private static void sessionUpdate(HttpSession session, MemberResponse response) {
        log.debug("세션 업데이트 - memberId: {}", response.getMemberId());
        session.setAttribute("loginMember", response);
    }
    private void handleMessage(RedirectAttributes redirectAttributes, String message){
        log.debug("리다이렉트 메시지 설정: {}", message);
        redirectAttributes.addFlashAttribute("message", message);
    }
    private void handleError(RedirectAttributes redirectAttributes, Exception e){
        log.error("에러 발생: {}", e.getMessage());
        redirectAttributes.addFlashAttribute("error", e.getMessage());
    }

}
