package org.example.test.mapper;

import lombok.*;
import org.example.test.Dto.ProductDTO;
import org.example.test.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductMapper() {
        // Khởi tạo nếu cần
    }
    public static ProductDTO productDTO(Product product){
        return new ProductDTO(product.getId(), product.getName(),product.getDescription(),product.getCategory(), product.getDiscount(), product.getThumbnail(),product.getListImage()
                ,product.getStatus(), product.getPrice(),product.getRate());
    }
}
