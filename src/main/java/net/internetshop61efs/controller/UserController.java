package net.internetshop61efs.controller;

import lombok.RequiredArgsConstructor;
import net.internetshop61efs.controller.api.UserApi;
import net.internetshop61efs.dto.MessageResponseDto;
import net.internetshop61efs.dto.UserResponseDto;
import net.internetshop61efs.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService service;


    @Override
    public ResponseEntity<UserResponseDto> getUserById(long userId) {
        return ResponseEntity.ok(service.getUserById(userId));
    }

    @GetMapping("/photolink")
    public ResponseEntity<MessageResponseDto> setPhotoLink(@RequestParam String fileLink){
        return ResponseEntity.ok(service.setPhotoLink(fileLink));
    }
}
