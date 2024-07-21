package org.example.test.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.test.Dto.ProductDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithComment {
    private ProductDTO productDTO;
    private List<Comment> comments;
    private List<ProductDTO> relatedProducts;
}
