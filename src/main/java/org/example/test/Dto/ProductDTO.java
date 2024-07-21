package org.example.test.Dto;

import lombok.*;
import org.example.test.entity.Category;
import org.example.test.entity.Discount;
import org.example.test.entity.Rate;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDTO {
    private Integer id ;
    private String name ;
    private String description;
    private Category category;
    private Discount discount;
    private String thumbnail;
    private List<String> ListImage;
    private Boolean status;
    private Double price;
    private Rate rate;



}
