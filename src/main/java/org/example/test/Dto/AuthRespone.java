package org.example.test.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthRespone {
    private UserDto auth;
    private String token;
    @JsonProperty("is_Authenticated") // custom dữ liệu trả vef
    private Boolean isAuthenticated;
}
