package org.example.test.mapper;

import org.example.test.Dto.UserDto;
import org.example.test.entity.User;

public class UserMapper {
    public static UserDto userDto(User user){
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getAvatar(), user.getRoles());
    }
}
