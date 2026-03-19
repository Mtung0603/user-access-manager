package com.r2s.auth.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // Chuyển hướng hoặc trả về lỗi
        //response.sendRedirect("/403");
        System.out.println("[ACCESS DENIED HANDLER] 403 Forbidden triggered for URL: " + request.getRequestURI());
        System.out.println("Denied reason: " + accessDeniedException.getMessage());
        accessDeniedException.printStackTrace();
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("text/plain; charset=UTF-8");
        response.getWriter().write("Access Denied - Ban khong co quyen");
    }
}


