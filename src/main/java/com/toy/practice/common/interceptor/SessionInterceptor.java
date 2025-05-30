package com.toy.practice.common.interceptor;

import com.toy.practice.member.dto.MemberResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            MemberResponse loginMember = (MemberResponse) session.getAttribute("loginMember");
            if (loginMember != null) {
                request.setAttribute("member", loginMember);
            }
        }
        return true;
    }
} 