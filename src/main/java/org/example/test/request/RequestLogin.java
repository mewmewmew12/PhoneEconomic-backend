package org.example.test.request;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestLogin {
    @NotEmpty(message = "Không được để trống")
    private String email;
    @NotEmpty(message = "Không được để trống")
    private String password;
}
