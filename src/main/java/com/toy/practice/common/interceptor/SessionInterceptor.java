package com.toy.practice.common.interceptor;

import com.toy.practice.member.dto.MemberResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("인증 체크 인터셉터 실행 {}", requestURI);

        // 로그인 체크가 필요한 URL 패턴
        if (isLoginCheckPath(requestURI)) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("loginMember") == null) {
                log.info("미인증 사용자 요청");
                // 로그인으로 redirect
                response.sendRedirect("/members/login");
                return false;
            }
        }

        // 관리자 권한이 필요한 URL 패턴
        if (isAdminCheckPath(requestURI)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                MemberResponse loginMember = (MemberResponse) session.getAttribute("loginMember");
                if (loginMember == null || !isAdmin(loginMember)) {
                    log.info("권한 없는 사용자 요청");
                    response.sendRedirect("/error/403");
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("postHandle 실행 - URI: {}", request.getRequestURI());
        if (modelAndView != null) {
            HttpSession session = request.getSession(false);
            log.debug("세션 존재 여부: {}", session != null);
            if (session != null) {
                MemberResponse loginMember = (MemberResponse) session.getAttribute("loginMember");
                log.debug("loginMember 존재 여부: {}", loginMember != null);
                if (loginMember != null) {
                    log.debug("세션 정보 모델에 추가 - memberId: {}, name: {}", loginMember.getMemberId(), loginMember.getName());
                    modelAndView.addObject("member", loginMember);
                    log.debug("모델에 추가된 member 객체: {}", modelAndView.getModel().get("member"));
                }
            }
        } else {
            log.debug("modelAndView가 null입니다.");
        }
    }

    /**
     * 로그인 체크가 필요한 URL 패턴인지 확인
     */
    private boolean isLoginCheckPath(String requestURI) {
        return requestURI.startsWith("/members/detail") ||
               requestURI.startsWith("/members/edit");
    }

    /**
     * 관리자 권한 체크가 필요한 URL 패턴인지 확인
     */
    private boolean isAdminCheckPath(String requestURI) {
        return requestURI.startsWith("/admin/") ||
               requestURI.startsWith("/members/delete");
    }

    /**
     * 관리자 권한 확인
     */
    private boolean isAdmin(MemberResponse member) {
        return member.getRole().name().equals("ADMIN");
    }
} 