package net.internetshop61efs.service;

import lombok.RequiredArgsConstructor;
import net.internetshop61efs.dto.UserRequestDto;
import net.internetshop61efs.dto.UserResponseDto;
import net.internetshop61efs.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Converter {

    public UserResponseDto fromUser(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    public User fromDto(UserRequestDto request){
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .hashPassword(request.getHashPassword())
                .build();
    }

    public List<UserResponseDto> fromUser(List<User> users){
        return users.stream()
                .map(user -> fromUser(user))
                .toList();
    }


}
