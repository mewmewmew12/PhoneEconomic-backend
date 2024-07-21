package org.example.test.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.test.respone.ErrorRespone;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    // xử lsy ỗi 401
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorRespone mesage = new ErrorRespone(HttpStatus.UNAUTHORIZED,"Bạn cần đăng nhập");

        ObjectMapper objectMapper = new ObjectMapper();
        String mesageJSON = objectMapper.writeValueAsString(mesage);

        response.addHeader("Content-type","application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mesageJSON);
    }
}
