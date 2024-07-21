package org.example.test.service;

import org.example.test.entity.Cart;
import org.example.test.entity.Payment;
import org.example.test.entity.User;
import org.example.test.repository.CartRepository;
import org.example.test.repository.PaymentRepository;
import org.example.test.repository.ProductRepository;
import org.example.test.repository.UserRepository;
import org.example.test.request.CreatePay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository ;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    public ResponseEntity<?> createPay(CreatePay requestPay){
        String token = UUID.randomUUID().toString();
        User user = userRepository.findById(requestPay.getUserId()).orElse(null);
        Cart cart = cartRepository.findById(requestPay.getCartId()).orElse(null);
        Double totalPrice = cart.getTotalPrice();
        Payment payment = Payment.builder()
                .user(user)
                .cart(cart)
                .address(requestPay.getAddress())
                .price(totalPrice)
                .phone(requestPay.getPhone())
                .build();
        paymentRepository.save(payment);

        return ResponseEntity.status(HttpStatus.OK).body("Tạo đơn hàng thành công ");
    }
}
