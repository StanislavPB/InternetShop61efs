package net.internetshop61efs.controller;

import lombok.RequiredArgsConstructor;
import net.internetshop61efs.controller.api.AdminApi;
import net.internetshop61efs.dto.UserResponseDto;
import net.internetshop61efs.entity.ConfirmationCode;
import net.internetshop61efs.entity.User;
import net.internetshop61efs.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminApi {
    private final UserService service;


    @Override
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Override
    public ResponseEntity<List<User>> findAllFull() {
        return ResponseEntity.ok(service.findAllFullDetails());
    }

    @Override
    public ResponseEntity<List<ConfirmationCode>> findAllCodes(String email) {
        return ResponseEntity.ok(service.findCodesByEmail(email));
    }
}
