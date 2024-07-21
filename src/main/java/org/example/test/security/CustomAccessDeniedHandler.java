package org.example.test.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.test.respone.ErrorRespone;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    // xử lý looix 403
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ErrorRespone mesage = new ErrorRespone(HttpStatus.FORBIDDEN,"Bạn không có quyền truy cập");

        ObjectMapper objectMapper = new ObjectMapper();
        String mesageJSON = objectMapper.writeValueAsString(mesage);

        response.addHeader("Content-type","application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mesageJSON);
    }
}
