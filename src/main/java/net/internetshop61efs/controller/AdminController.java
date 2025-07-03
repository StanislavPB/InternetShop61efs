package net.internetshop61efs.controller;

import lombok.RequiredArgsConstructor;
import net.internetshop61efs.controller.api.AdminApi;
import net.internetshop61efs.dto.UserRequestDto;
import net.internetshop61efs.dto.UserResponseDto;
import net.internetshop61efs.entity.User;
import net.internetshop61efs.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController implements AdminApi {

    private final UserService service;

    //* найти всех пользователей (полная информация - для ADMIN)
    @GetMapping("/full")
    public ResponseEntity<List<User>> findAllFullDetails(){
        return ResponseEntity.ok(service.findFullDetailsUsers());
    };

    //* найти всех пользователей (ограниченная информация - для MANAGER)
    @GetMapping("/manager/all")
    public ResponseEntity<List<UserResponseDto>> findAll(){
        return ResponseEntity.ok(service.findAllUsers());
    };


    @PutMapping("/update")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserRequestDto request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.updateUser(request));
    };

}
