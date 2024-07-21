package org.example.test.Dto;


import lombok.*;
import org.example.test.entity.Role;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
    private Integer id;
    private String name;
    private String email;
    private String avatar;
    private List<Role> roles;
}
