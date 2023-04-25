package com.mtj.travel.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    private final static String[] ALLOWED_URIS = {"/users/login", "/pages/login.html", "/index.html"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        for (String allowedUri : ALLOWED_URIS) {
            if (requestURI.endsWith(allowedUri)) {
                log.info("放行一个URL捏");
                return true;
            }
        }
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username == null) {
            // 用户未登录，重定向到登录页面
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}