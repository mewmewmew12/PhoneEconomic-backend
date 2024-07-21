package org.example.test.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.test.Dto.UserDto;
import org.example.test.entity.Cart;
import org.example.test.entity.Favorites;
import org.example.test.entity.OderItem;
import org.example.test.entity.User;
import org.example.test.repository.UserRepository;
import org.example.test.request.*;
import org.example.test.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private MailService mailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private OderItemService oderItemService;
    @Autowired
    private PaymentService paymentService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping(value = "updateUser/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<UserDto> updateUser(@PathVariable Integer userId, @RequestBody UpdateUser request){
        return userService.updateUser(userId,request);
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping(value = "changePassWord/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassWord(@PathVariable Integer userId,@Valid @RequestBody ChangePassWord request){
        userService.changePassword(userId,request);
        return ResponseEntity.status(HttpStatus.OK).body("Thay đổi pass thành công");
    }
//    @PreAuthorize("hasAnyRole('USER')")
//    @PreAuthorize("hasAnyRole('ROLE_USER','USER')")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = "addFavorite" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addFavoriteProduct(@RequestBody AddFavorite request){
        return userService.addFavorites(request);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = "removeFavorite" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeFavorite(@RequestBody AddFavorite requyest){
        return userService.removeFavorite(requyest);
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "getAllFavorite/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Favorites getAllFa(@PathVariable Integer userId){
        return userService.getFavoritesUser(userId);
    }

    @PostMapping(value = "addCartItem" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addProductItem(@RequestBody AddCartItem request){
        System.out.println("vào đây");
        return oderItemService.addCartItem(request);
    }
    @PostMapping(value = "removeCartItem" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeProductItem(@RequestBody RemoveCartItem request){
        System.out.println("vào đây");
        return oderItemService.removeCartItem(request);
    }

    @PostMapping(value = "payment" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPay(@RequestBody CreatePay requestPay){
        return paymentService.createPay(requestPay);
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "getOrderItemUser/{userId}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public Cart getOderItemUser(@PathVariable Integer userId){

        log.info("User Id "+userId);
        return oderItemService.getOrderItemWithUser(userId);
    }
}
