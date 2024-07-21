package org.example.test.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterResquest {
    @NotBlank(message = "Không được để trống")
    private String name;
    @NotEmpty(message = "Không đưược để trống")
    private String email;
    @NotEmpty(message = "không được để trống")
    @Min(value = 6 , message = "Tối thiểu phải có 6 kí tự")
    private String password;
    @NotEmpty(message = "Không được để trống")
    private String phone ;
    @NotBlank(message = "Không đuoc để trống")
    private String address;
}
