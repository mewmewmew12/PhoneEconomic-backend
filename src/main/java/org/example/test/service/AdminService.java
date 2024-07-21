package org.example.test.service;

import org.example.test.entity.Category;
import org.example.test.entity.Payment;
import org.example.test.entity.Product;
import org.example.test.entity.User;
import org.example.test.repository.*;
import org.example.test.request.CreateCategoryRequest;
import org.example.test.request.CreateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FavoritesRepository favoritesRepository;
    @Autowired
    private OderItemRepository oderItemRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }
    public List<Category> getAllCategory(){
        return categoryRepository.findAll() ;
    }
    public List<User> getAllUser(){
        return userRepository.findAll() ;
    }

    public List<Payment> getPayment(){
        return paymentRepository.findAll() ;
    }
    public Product createProduct(CreateProductRequest productRequest){
        Category category = categoryRepository.findById(productRequest.getCategoryid()).orElseThrow(() -> {
            throw new RuntimeException("không có danh muc này");
        });
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .thumbnail(productRequest.getThumbnail())
                .nums(productRequest.getNums())
                .price(productRequest.getPrice())
                .category(category)
                .build();
        productRepository.save(product);
        return product ;
    }

    public ResponseEntity<?> createCategory(CreateCategoryRequest request){
        Optional<Category> category = categoryRepository.findByNameIgnoreCase(request.getName());
        if(category.isPresent()){
            throw new RuntimeException("Category này đã có");
        }

        Category category1 = Category.builder()
//                    .thumbnail(request.getThumbnail())
                    .name(request.getName())
                    .build();
            categoryRepository.save(category1);
            return ResponseEntity.ok("tạo moi thanh cong");


    }
    public void deleteCategory(Integer categoryId){
        Category category = categoryRepository.findById(categoryId).orElse(null);
        List<Product> productList = productRepository.findByCategory_Id(categoryId);
        productRepository.deleteByCategory(category);
        categoryRepository.deleteById(categoryId);
    }
}
