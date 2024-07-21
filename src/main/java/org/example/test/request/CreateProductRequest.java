package org.example.test.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    @NotNull(message = "KHông được để trống ")
    private String name ;
    @NotEmpty(message = "Không được để trống")
    private String thumbnail;
    @NotBlank(message = "Bắt buoojc")
    private String description;
    private Integer nums;
    @NotBlank(message = "Bắt buộc")
    private Double price ;
    @NotEmpty(message = "Không đựơc để trống")
    private Integer categoryid;

}
