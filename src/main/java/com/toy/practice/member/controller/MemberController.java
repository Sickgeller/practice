package com.toy.practice.member.controller;

import com.toy.practice.member.dto.MemberRequest;
import com.toy.practice.member.dto.MemberResponse;
import com.toy.practice.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원 목록
    @GetMapping
    public String list(Model model) {
        List<MemberResponse> members = memberService.findAll().stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
        model.addAttribute("members", members);
        return "members/list";
    }

    // 회원가입 폼
    @GetMapping("/register")
    public String registerForm() {
        return "members/register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@ModelAttribute MemberRequest.SignUp request, RedirectAttributes redirectAttributes) {
        try {
            Long memberId = memberService.register(
                request.getId(),
                request.getName(),
                request.getEmail(),
                request.getPassword()
            );
            redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다.");
            return "redirect:/members/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/members/register";
        }
    }

    // 로그인 폼
    @GetMapping("/login")
    public String loginForm() {
        return "members/login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@ModelAttribute MemberRequest.Login request, RedirectAttributes redirectAttributes) {
        try {
            memberService.login(request.getId(), request.getPassword());
            redirectAttributes.addFlashAttribute("message", "로그인에 성공했습니다.");
            return "redirect:/members/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/members/login";
        }
    }

    // 회원 상세보기
    @GetMapping("/detail")
    public String detail(@RequestParam Long memberId, Model model) {
        model.addAttribute("member", MemberResponse.from(memberService.findById(memberId)));
        return "members/detail";
    }

    // 회원 수정 폼
    @GetMapping("/edit")
    public String editForm(@RequestParam Long memberId, Model model) {
        model.addAttribute("member", MemberResponse.from(memberService.findById(memberId)));
        return "members/edit";
    }

    // 회원 수정 처리
    @PostMapping("/edit")
    public String edit(@RequestParam Long memberId, @ModelAttribute MemberRequest.Update request, RedirectAttributes redirectAttributes) {
        try {
            System.out.println(request.getEmail());
            System.out.println(request.getName());
            memberService.update(memberId, request.getName(), request.getEmail());
            redirectAttributes.addFlashAttribute("message", "회원 정보가 수정되었습니다.");
            return "redirect:/members/detail?memberId=" + memberId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/members/edit?memberId=" + memberId;
        }
    }

    // 회원 삭제
    @PostMapping("/delete")
    public String delete(@RequestParam Long memberId, RedirectAttributes redirectAttributes) {
        try {
            memberService.delete(memberId);
            redirectAttributes.addFlashAttribute("message", "회원이 삭제되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/members/list";
    }
}
