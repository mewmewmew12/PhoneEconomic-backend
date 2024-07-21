package org.example.test.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest {
//    @NotNull(message = "không được để trosng")
    private String name ;
//    @NotNull(message = "Không được để trống")
//    private String thumbnail;
}
