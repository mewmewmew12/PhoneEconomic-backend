package org.example.test.service;

import org.example.test.Dto.CategoryDto;
import org.example.test.Dto.ProductDTO;
import org.example.test.Dto.projection.ProductInfo;
import org.example.test.entity.*;
import org.example.test.mapper.ProductMapper;
import org.example.test.repository.*;
import org.example.test.request.CreateCommentRequest;
import org.example.test.respone.BadResquestException;
import org.example.test.security.ImpliCurrent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ImpliCurrent iCurrentUser;

//    public List<Product> getSaleProduct(){
////        List<Product> products = productRepository.findAll();
////        List<Product> saleProduct = new ArrayList<>();
////        for(Product product : products){
////            if(product.getDiscount() != null){
////                saleProduct.add(product);
////            }
////        }
//        List<Product> productList = productRepository.findByDiscountNotNull();
//        return productList;
//    }
    public List<ProductInfo> getProductByDiscount(Integer percent){
        return productRepository.findByDiscount_Percent(percent);
    }
    public List<CategoryDto> getAllCategory(){
        return categoryRepository.findAll().stream().map(
                e ->{
                    return new CategoryDto(e.getId() , e.getName(), e.getThumbnail());
                }
        ).toList();
    }
    public List<ProductDTO> getAll(){
        return productRepository.findAll().stream().map(e ->{
            return new ProductDTO(e.getId(),e.getName(),e.getDescription(),e.getCategory(),e.getDiscount(),e.getThumbnail(),e.getListImage(),e.getStatus(),
                    e.getPrice(),e.getRate()) ;
        }).toList();
    }

    public Page<ProductDTO> getProductBestSell(Integer view , Double sale , Pageable pageable){
         Page<Product> productList =  productRepository.findByViewOrSales(view,sale,pageable);
         Page<ProductDTO> productDTOS = productList.map(ProductMapper::productDTO);
        return productDTOS;
    }
    public ProductWithComment getProductWithComment(Integer productId){
        Product product = productRepository.findById(productId).orElse(null);
//        ProductDTO productDTO = ProductMapper.productDTO(product);
        List<Comment> comments = commentRepository.findByProduct(product);
        Product productOrigi = productRepository.findById(productId).orElse(null);
        product.setView(productOrigi.getView() + 1);
        productRepository.save(product);
        ProductDTO productDTO = ProductMapper.productDTO(product);

        Category category = product.getCategory();
        List<Product> relatedProducts = productRepository.findByCategoryAndIdNot(category, productId);
        List<ProductDTO> relatedProductDto = relatedProducts.stream()
                .map(ProductMapper::productDTO)
                .collect(Collectors.toList());
        return new ProductWithComment(productDTO,comments,relatedProductDto);
    }
    public Comment createComment(Integer productId,CreateCommentRequest request){
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> {
                    throw new RuntimeException("Email này không dúng");
                }
                );
        Product product = productRepository.findById(productId).orElse(null);
//        Role role = roleRepository.findByName("USER").orElse(null);
//        if( !user.getRoles().equals(role)){
//            throw new BadResquestException("Bạn không có quyền Comment bài viết này");
//        } else {
            Comment comment = Comment.builder()
                    .product(product)
                    .user(user)
                    .content(request.getContent())
                    .build();
            commentRepository.save(comment);
            return comment;
    }
    public void deleteComment(Integer commentId){
        User user = iCurrentUser.getUser();
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> {
                    throw new BadResquestException("không tim thay comment");
                }
        );
        Role role = roleRepository.findByName("USER").orElse(null);

        if (user.getId() != comment.getUser().getId() && !user.getRoles().equals(role)){
            throw new BadResquestException("Bạn không có quyền xoá comment nafy");
        }
        commentRepository.deleteById(commentId);
    }
    public List<ProductDTO> getProductByCategoryName(String categoryname){
        List<Product> product = productRepository.findByCategory_NameIgnoreCase(categoryname);
        List<ProductDTO> productDTOS = product.stream().map(ProductMapper::productDTO).collect(Collectors.toList());
        return productDTOS;
    }
}
