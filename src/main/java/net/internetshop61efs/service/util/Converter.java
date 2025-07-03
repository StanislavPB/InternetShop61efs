package net.internetshop61efs.service.util;

import net.internetshop61efs.dto.UserRequestDto;
import net.internetshop61efs.dto.UserResponseDto;
import net.internetshop61efs.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class Converter {

    public User fromDto(UserRequestDto dto){

//        User newUser = new User();
//        newUser.setFirstName(dto.getFirstName());
//        newUser.setLastName(dto.getLastName());
//        newUser.setEmail(dto.getEmail());
//        newUser.setHashPassword(dto.getPassword());
//
//        return newUser;

        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .hashPassword(dto.getPassword())
                .build();
    }

    public UserResponseDto toDto(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    public List<UserResponseDto> fromUsers(List<User> users){
        return users.stream()
                .map(user -> toDto(user))
                .toList();
    }

}
