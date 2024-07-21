package org.example.test.service;

import org.example.test.Dto.AuthRespone;
import org.example.test.entity.Role;
import org.example.test.entity.Token;
import org.example.test.entity.User;
import org.example.test.mapper.UserMapper;
import org.example.test.repository.RoleRepository;
import org.example.test.repository.TokenRepository;
import org.example.test.repository.UserRepository;
import org.example.test.request.RegisterResquest;
import org.example.test.request.RequestLogin;
import org.example.test.respone.BadResquestException;
import org.example.test.respone.ConfirmTokenException;
import org.example.test.respone.NotFoundException;
import org.example.test.security.CustomUserDetailService;
import org.example.test.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils ;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;

    public AuthRespone loginUser(RequestLogin request) throws AuthenticationException {
        // Tao doi tuong xac thuwjc
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword());
        try{
            // tiên hanh
            Authentication authentication = authenticationManager.authenticate(token);
            // lưu dữ liệu vào context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = customUserDetailService.loadUserByUsername(authentication.getName());
            String jwtToken = jwtUtils.generateToken(userDetails);
            User user = userRepository.findByEmailLikeIgnoreCase(authentication.getName()).orElse(null);

            return
                    new AuthRespone(
                    UserMapper.userDto(user),
                    jwtToken,
                    true
            );
        } catch (Exception ex){
            throw new BadCredentialsException(ex.getMessage());
        }
    }
    // Đăng kí tài khoản vơới email : check tài khoản với email, tạo user và gửi mã token để xác nhajan
    public ResponseEntity<?> registerUser(RegisterResquest resquest){
        Optional<User> user = userRepository.findByEmail(resquest.getEmail());
        if (user.isPresent()){
            User user1 = user.get();
            if(user.get().isEnabled()){
                throw new BadResquestException("Email nafy chua dawng ki");
            }else{
                String token = UUID.randomUUID().toString();
                Token token1 = Token.builder()
                        .tokenUser(token)
                        .user(user1)
                        .build();
                tokenRepository.save(token1);
                mailService.sendEmail(resquest.getEmail() , "Xác thực tài khoản thành công ", token1.getTokenUser());
                return ResponseEntity.ok("tài khoản xac minh");
            }
        } else {
            Role role = roleRepository.findByName("USER").orElse(null);
            User userNew = User.builder()
                    .name(resquest.getName())
                    .email(resquest.getEmail())
                    .password(encoder.encode(resquest.getPassword()))
                    .phone(resquest.getPhone())
                    .address(resquest.getAddress())
                    .isEnable(false)
                    .roles(List.of(role))
                    .build();
            userRepository.save(userNew);
            String tokenNew = UUID.randomUUID().toString();
            Token token = Token.builder()
                    .tokenUser(tokenNew)
                    .user(userNew)
                    .build();
            tokenRepository.save(token);
            mailService.sendEmail(resquest.getEmail(), "đăng kí tài khoản thành công" , token.getTokenUser());
            return ResponseEntity.ok("Đăng kis tài khoản thành công");
        }

    }
    public ResponseEntity<?> confirmUser(String tokenUser) {
        Optional<Token> token = tokenRepository.findByTokenUser(tokenUser);
        if (token.isEmpty()) {
            throw new BadResquestException("Không tìm thấy Token này !!! ");
        }
        if (LocalDateTime.now().isAfter(token.get().getExpiresAt())) {
            String newUUID = UUID.randomUUID().toString();
            Token tokenNew = Token.builder()
                    .user(token.get().getUser())
                    .tokenUser(newUUID)
                    .build();
            tokenRepository.save(tokenNew);
            mailService.sendEmail(token.get().getUser().getEmail(), "xác minh lại tài khoản của bạn", "Mã Token của bạn : " + tokenNew.getTokenUser());
            throw new ConfirmTokenException("Token này đã hết hạn , chúng tôi đã gửi 1 mã Token mới cho bạn tại Email , hãy điền Token mới vào đây !! ");
        }
        User user = token.get().getUser();
        user.setIsEnable(true);
        userRepository.save(user);
        return ResponseEntity.ok("Xác Thực thành công ");
    }

    public ResponseEntity<?> confirmEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new BadResquestException("Không tìm thấy Email của bạn hãy kiểm tra lại email đã nhập ");
        }
        String tokenConfirm = UUID.randomUUID().toString();
        Token token = Token.builder()
                .tokenUser(tokenConfirm)
                .user(userOptional.get())
                .build();
        tokenRepository.save(token);
        mailService.sendEmail(userOptional.get().getEmail(), "Xác minh tài khoản ", "Mã Code để xác minh tài khoản của bạn : " + token.getTokenUser());
        return ResponseEntity.ok("Chúng tôi đã gửi mã code qua Email của bạn !!");
    }

    public ResponseEntity<?> confirmTokenAndEmail(String email, String token) {
        Optional<User> user = userRepository.findByEmail(email);
        Optional<Token> tokenOptional = tokenRepository.findByTokenUser(token);
        if(tokenOptional.isEmpty()){
            throw new NotFoundException("Mã Token không đúng , hoặc không hợp lệ , vui lòng kiểm tra lại");
        }
        if(user.get().getId() != tokenOptional.get().getUser().getId()){
            throw new ConfirmTokenException("Token này không hợp lệ");
        }
        if(tokenOptional.get().getExpiresAt().isAfter(LocalDateTime.now().plusMinutes(5))){
            throw new BadResquestException("Mã Token này đã hết hạn");
        }
        String passNew = UUID.randomUUID().toString();
        user.get().setPassword(encoder.encode(passNew));
        userRepository.save(user.get());
        mailService.sendEmail(email,"Mật Khẩu Mới Của Bạn","Mật Khẩu : "+passNew);
        return ResponseEntity.ok("Xác thực Token thành công , chúng tôi đã gửi cho bạn mật khẩu mới ở Email của bạn !! hãy dùng đăng nhập và vào đổi mật khẩu .. ");
    }


}
