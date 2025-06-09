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

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberAppService memberAppService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String loginForm() {
        log.info("로그인 폼 요청");
        return "members/login";
    }

    @GetMapping("/register")
    public String registerForm() {
        log.info("회원가입 폼 요청");
        return "members/register";
    }

    @GetMapping("/edit")
    public String editForm(){
        log.info("회원 수정 폼 요청");
        return "members/edit";
    }



    @PostMapping("/register")
    public String register(@ModelAttribute MemberSignUpRequest request, RedirectAttributes redirectAttributes) {
        log.info("회원가입 요청: {}", request);
        try {
            memberAppService.register(request);
            redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다.");
            return "redirect:/members/login";
        } catch (MemberException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/members/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        log.info("로그아웃 요청");
        session.invalidate();
        return "redirect:/members/login";
    }
}
