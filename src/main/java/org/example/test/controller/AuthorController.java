package org.example.test.controller;

import jakarta.validation.Valid;
import org.example.test.Dto.AuthRespone;
import org.example.test.repository.UserRepository;
import org.example.test.request.ForgotPassWord;
import org.example.test.request.RegisterResquest;
import org.example.test.request.RequestLogin;
import org.example.test.security.CustomUserDetailService;
import org.example.test.security.JwtUtils;
import org.example.test.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/auth")
public class AuthorController {
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;

    @PostMapping(value = "login",produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthRespone login(@RequestBody RequestLogin request) throws AuthenticationException {
        System.out.println("Đã vào ");
        AuthRespone authRespone = authService.loginUser(request);
        return authRespone;
    }

    @PostMapping(value = "register" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterResquest resquest){
        authService.registerUser(resquest);
        System.out.println("đăng kí thành công" + resquest.getName());
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
    @PostMapping(value = "forgotPassWord", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> forgotPassWord(@Valid @RequestBody ForgotPassWord request){
        authService.confirmTokenAndEmail(request.getEmail(), request.getToken());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
//        // tiến hành xác thực
//        Authentication authentication = authenticationManager.authenticate(token);
//
//        // lưu dữ liệu vào context
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // tạo ra session
//        session.setAttribute("My Session",authentication.getName());