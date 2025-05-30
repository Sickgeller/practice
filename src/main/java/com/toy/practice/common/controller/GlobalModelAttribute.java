package com.toy.practice.common.controller;

import com.toy.practice.member.dto.MemberResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttribute {

    @ModelAttribute("member")
    public MemberResponse addMemberToModel(HttpSession session){
        return (MemberResponse) session.getAttribute("loginMember");
    }
}
