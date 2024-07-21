package org.example.test.request;

import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddCartItem {
    private Integer userId;
    private Integer productId;
    @Min(1)
    private Integer nums;
}
