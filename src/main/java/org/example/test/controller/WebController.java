package org.example.test.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.test.Dto.CategoryDto;
import org.example.test.Dto.ProductDTO;
import org.example.test.Dto.projection.ProductInfo;
import org.example.test.entity.Category;
import org.example.test.entity.Comment;
import org.example.test.entity.Product;
import org.example.test.entity.ProductWithComment;
import org.example.test.mapper.ProductMapper;
import org.example.test.repository.ProductRepository;
import org.example.test.request.CreateCategoryRequest;
import org.example.test.request.CreateCommentRequest;
import org.example.test.service.AdminService;
import org.example.test.service.OderItemService;
import org.example.test.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/v1/public")
public class WebController {
    @Autowired
    private WebService webService;
    private final ProductRepository productRepository;

    public WebController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Autowired
    private OderItemService oderItemService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private AdminService adminService;

    @GetMapping(value = "getDiscountProduct/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductInfo> getDiscountProduct(@RequestParam Integer percent){
        return webService.getProductByDiscount(percent);
    }
    @GetMapping(value = "getCategoryById/{categoryId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDTO> getProductByCategory(@PathVariable Integer categoryId){
        List<Product> productList =  productRepository.findByCategory_Id(categoryId) ;
        List<ProductDTO> productDTOList = productList.stream()
                .map(ProductMapper::productDTO)
                .collect(Collectors.toList());
        return productDTOList ;
    }

    @GetMapping(value = "getCategory", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryDto> getAllCategory(){
        return webService.getAllCategory();
    }

    @GetMapping(value = "getAllProduct" , produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDTO> getAllProduct(){
        return webService.getAll();
    }

    @GetMapping(value = "getSellProduct/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ProductDTO> getSellProduct(@RequestParam Integer view,
                                        @RequestParam Double sale,
                                        @RequestParam Integer page,
                                        @RequestParam Integer size) {
        PageRequest pageable = PageRequest.of(0,4);
        System.out.println("vào đây");
        return webService.getProductBestSell(view,(Double) sale,pageable);

    }

    @GetMapping(value = "getProductFindId/{productId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductWithComment getProductWithComment(@PathVariable Integer productId){
        return  webService.getProductWithComment(productId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = "createCommetn/{productId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Comment createCommet(@PathVariable Integer productId,@RequestBody CreateCommentRequest request){
        return webService.createComment(productId, request);
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping(value = "deleteComment/{commentId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteCommet(@PathVariable Integer commentId){
        log.info("CommentId"+commentId);
        webService.deleteComment(commentId);
    }


    @GetMapping(value = "getProductByCategory/search" , produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDTO> getProductByCategory(@RequestParam String categoryName){
        return webService.getProductByCategoryName(categoryName);
    }
    @PostMapping(value = "createCategory", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCategory(@RequestBody CreateCategoryRequest request){
        return adminService.createCategory(request);
    }
}


//    @GetMapping("/")
//    public String getHome(Model model) {
//        model.addAttribute("name", "Bùi HIên");
//        List<String> users = List.of("Sơn", "Khôi", "Hưởng");
//        model.addAttribute("users", users);
//        return "security";
//    }
//    @GetMapping("/user")
//    public String userPage(Model model) {
//        return "user";
//    }
//    @GetMapping("/admin")
//    public String adminPage(Model model) {
//        return "admin";
//    }
//    @GetMapping("/author")
//    public String authorPage(Model model) {
//        return "author";
//    }
//    @GetMapping("/login")
//    public String login(Model model) {
//        return "login";
//    }

//    @GetMapping("getProductFindId/{productId}")
//    public ResponseEntity<?> getProductWithComment(@PathVariable Integer productId) {
//        ProductWithComment productWithComment = webService.getProductFindId(productId);
//
//        if(productWithComment != null) {
//            return ResponseEntity.ok(productWithComment);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }