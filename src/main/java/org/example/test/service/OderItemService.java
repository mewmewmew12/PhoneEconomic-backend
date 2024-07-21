package org.example.test.service;

import org.example.test.entity.Cart;
import org.example.test.entity.OderItem;
import org.example.test.entity.Product;
import org.example.test.entity.User;
import org.example.test.repository.CartRepository;
import org.example.test.repository.OderItemRepository;
import org.example.test.repository.ProductRepository;
import org.example.test.repository.UserRepository;
import org.example.test.request.AddCartItem;
import org.example.test.request.RemoveCartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OderItemService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OderItemRepository oderItemRepository;
    @Autowired
    private CartRepository cartRepository;

    public ResponseEntity<?> addCartItem(AddCartItem request){
        Optional<User> user = userRepository.findById(request.getUserId());
        Optional<Product> product = productRepository.findById(request.getProductId());
        Optional<Cart> cart = cartRepository.findByUser_Id(request.getUserId());
        if(!cart.isPresent()){
            OderItem item = OderItem.builder()
                    .product(product.get())
                    .nums(request.getNums())
                    .build();
            Set<OderItem> items = new LinkedHashSet<>();
            items.add(item);
            oderItemRepository.save(item);
            Cart cart1 = Cart.builder()
                    .user(user.get())
                    .oderItems(items)
                    .build();
            cartRepository.save(cart1);
        } else {
//            Optional<Cart> cart1 = cartRepository.findByUser_Id(request.getUserId());
            Cart existingCart = cart.get();
            boolean productExistsInCart = false;
            for(OderItem item : existingCart.getOderItems()){
                if (item.getProduct().getId().equals(request.getProductId()) ){
                    int newQuantity = item.getNums() + request.getNums();
                    if (item.getProduct().getNums() < newQuantity){  /* request.getNums() */
                        throw new RuntimeException("Số lượng sản phẩm không đủ");
                    }
                    Product product1 = productRepository.findById(item.getProduct().getId()).orElseThrow(
                            () ->{
                                throw new RuntimeException("Khong tìm thấy sản phẩm này");
                            }
                    );
                    item.setNums(newQuantity); /* request.getNums() */
                    if(product1.getDiscount() == null){
                        item.setPrice(product1.getPrice() * newQuantity);  /* request.getNums() */
                    } else {
                        item.setPrice(product1.getSales() * newQuantity);  /* request.getNums() */
                    }
                    oderItemRepository.save(item);
                    productExistsInCart = true;
                    break;
                }
            }
            if (!productExistsInCart) {
                // Sản phẩm chưa tồn tại trong giỏ hàng, thêm mới
                OderItem newItem = OderItem.builder()
                        .product(product.get())
                        .nums(request.getNums())
                        .build();
                existingCart.getOderItems().add(newItem);
                oderItemRepository.save(newItem);
            }
            cartRepository.save(existingCart);
        }

        return ResponseEntity.status(HttpStatus.OK).body("Thêm sản phẩm vào giỏ hàng thành công");
    }
    public ResponseEntity<?> removeCartItem(RemoveCartItem request){
        Optional<User> user = userRepository.findById(request.getUserId());
        Optional<Product> product = productRepository.findById(request.getProductId());
        Optional<Cart> cart = cartRepository.findByUser_Id(request.getUserId());
        Optional<OderItem> itemToRemove = cart.get().getOderItems().stream()
                .filter(item -> item.getProduct().getId().equals(request.getProductId()))
                .findFirst();
        cart.get().setTotalPrice(request.getProductId().doubleValue());

        if (!itemToRemove.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy mục hàng với id sản phẩm: " + request.getProductId());
        }

        // Xóa mục hàng khỏi giỏ hàng
        cart.get().getOderItems().remove(itemToRemove.get());
        cartRepository.save(cart.get());
        return ResponseEntity.status(HttpStatus.OK).body("Đã xoá sản phẩm thành công");
    }
    public Cart getOrderItemWithUser(Integer userId){
//        User user = userRepository.findById(userId).orElse(null);
        Optional<Cart> cart = cartRepository.findByUser_Id(userId);
        return cart.get();
    }

}
//        for (OderItem item : cart.getOderItems()){
//            if(item.getProduct().getId() == request.getProductId()){
//                cart.getOderItems().remove(item);
//                oderItemRepository.delete(item);
//                cartRepository.save(cart);
//            }
//
//        }