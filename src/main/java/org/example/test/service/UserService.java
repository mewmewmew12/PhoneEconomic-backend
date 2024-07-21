package org.example.test.service;

import org.example.test.Dto.UserDto;
import org.example.test.entity.Favorites;
import org.example.test.entity.Product;
import org.example.test.entity.User;
import org.example.test.mapper.UserMapper;
import org.example.test.repository.FavoritesRepository;
import org.example.test.repository.ProductRepository;
import org.example.test.repository.UserRepository;
import org.example.test.request.AddFavorite;
import org.example.test.request.ChangePassWord;
import org.example.test.request.UpdateUser;
import org.example.test.respone.BadResquestException;
import org.example.test.security.ICurrent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private FavoritesRepository favoritesRepository ;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ICurrent iCurrent;

    public Optional<UserDto> updateUser(Integer userId, UpdateUser request){
        Optional<User> userOptional = userRepository.findById(userId);

        if(!userOptional.isPresent()){
            throw new BadResquestException("User này không có");
        }
        User user = userOptional.get();
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        userRepository.save(user);
        UserDto userDto = UserMapper.userDto(user);
        return Optional.of(userDto) ;
    }
    public ResponseEntity<?> changePassword(Integer userId ,ChangePassWord request){
        String oldPass = encoder.encode(request.getOldPassWord());
        String newPass = encoder.encode(request.getNewPassWord());
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()){
            throw new BadResquestException("User này ko có");
        }
        if (!encoder.matches(request.getOldPassWord(), user.get().getPassword())){
            throw new BadResquestException("PassWord không đúng");
        }else{
            user.get().setPassword(encoder.encode(request.getNewPassWord()));
            userRepository.save(user.get());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Thay đổi mật khẩu thành công");
    }
    public ResponseEntity<?> addFavorites(AddFavorite request) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentUsername = authentication.getName();
       User user = userRepository.findById(request.getUserId()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Khong tim thay nguoi dung");
        }
        Product product = productRepository.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Khong tim thay san pham");
        }
        Boolean checkFavorites = favoritesRepository.existsByUser_Id(request.getUserId());
        if (!checkFavorites) {
            Set<Product> items = new HashSet<>();
            items.add(product);
            Favorites favorites = Favorites.builder()
                    .user(user)
                    .products(items)
                    .build();
            favoritesRepository.save(favorites);
        } else {
            Favorites favorites = favoritesRepository.findByUser_Id(request.getUserId());
            if(favorites.getProducts().contains(product)){
                throw new RuntimeException("San pham nay da duoc yeu thisch");
            }else {
                favorites.getProducts().add(product);
            }
            favoritesRepository.save(favorites);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Da them san pham vao muc yeu thich");
    }
    public ResponseEntity<?> removeFavorite(AddFavorite request){
        User user = userRepository.findById(request.getUserId()).orElse(null);
        Product product = productRepository.findById(request.getProductId()).orElse(null);
        Favorites favorites = favoritesRepository.findByUser_Id(request.getUserId());
        favorites.getProducts().remove(product);
        favoritesRepository.save(favorites);
        return ResponseEntity.status(HttpStatus.OK).body("Xoa san pham khoi muc yeu thich");
    }

    public Favorites getFavoritesUser(Integer userId){
        User user = userRepository.findById(userId).orElseThrow(() ->{
            throw new RuntimeException("khong tim thay User nafy");
        });
        Favorites favorites = favoritesRepository.findByUser_Id(userId);
        return favorites;
    }
}
