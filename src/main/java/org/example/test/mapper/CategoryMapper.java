package org.example.test.mapper;

import org.example.test.Dto.CategoryDto;
import org.example.test.entity.Category;

public class CategoryMapper {
    public static CategoryDto categoryDto(Category category){
        return new CategoryDto(category.getId(), category.getName(), category.getThumbnail());
    }
}
