package org.example.test.security;

import lombok.RequiredArgsConstructor;
import org.example.test.entity.User;
import org.example.test.repository.UserRepository;
import org.example.test.respone.BadResquestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImpliCurrent implements ICurrent{
    @Autowired
    private UserRepository userRepository;
    @Override
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName()).orElseThrow(
                ()->{
                    throw new BadResquestException("Khong tim thay User" + authentication.getName());
                }
        );
    }
}
