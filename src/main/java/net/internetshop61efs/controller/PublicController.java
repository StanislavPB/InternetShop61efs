package net.internetshop61efs.controller;

import lombok.RequiredArgsConstructor;
import net.internetshop61efs.controller.api.PublicApi;
import net.internetshop61efs.dto.UserRequestDto;
import net.internetshop61efs.dto.UserResponseDto;
import net.internetshop61efs.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PublicController implements PublicApi {

    private final UserService userService;

    @Override
    public ResponseEntity<UserResponseDto> userRegistration(UserRequestDto request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.registration(request));
    }

    @Override
    public ResponseEntity<UserResponseDto> confirmRegistration(String code) {
        return ResponseEntity.ok(userService.confirmation(code));
    }
}
