package org.example.test.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePassWord {
    @Email(message = "Email không hợp lệ")
    private String email;
    @Min(value = 6 , message = "Tối thiểu phải có 6 kí tự")
    private String oldPassWord;
    @Min(value = 6 , message = "Tối thiểu phải có 6 kí tự")
    private String newPassWord;
}
