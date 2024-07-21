package org.example.test.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ForgotPassWord {
    @NotEmpty(message = "Không đựơc để trống")
    private String email ;
    private String token ;
}
