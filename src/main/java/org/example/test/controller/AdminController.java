package org.example.test.controller;

import jakarta.validation.Valid;
import org.example.test.entity.Category;
import org.example.test.entity.Product;
import org.example.test.entity.User;
import org.example.test.request.CreateCategoryRequest;
import org.example.test.request.CreateProductRequest;
import org.example.test.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping(value = "/getProduct", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getProduct(){
        return adminService.getAllProduct();
    }
    @GetMapping(value = "/getCategory", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> getCategory(){
        return adminService.getAllCategory();
    }
    @GetMapping(value = "/getUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUser(){
        return adminService.getAllUser();
    }

//    @PostMapping(value = "CreateCategory",produces = MediaType.APPLICATION_JSON_VALUE)
//    public Category CreateCategory(@Valid CreateCategoryRequest request){
//        return adminService.createCategory(request);
//    }
//
//    @PostMapping(value = "CreateProduct",produces = MediaType.APPLICATION_JSON_VALUE)
//    public Product CreateCategory(@Valid CreateProductRequest request){
//        return adminService.createProduct(request);
//    }

}
