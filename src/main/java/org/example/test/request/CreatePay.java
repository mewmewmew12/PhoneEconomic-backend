package org.example.test.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePay {
    private Integer userId;
    private Integer cartId;
    private String address;
    private String phone;

}
