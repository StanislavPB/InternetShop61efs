package net.internetshop61efs.controller.api;

import net.internetshop61efs.dto.UserResponseDto;
import net.internetshop61efs.entity.ConfirmationCode;
import net.internetshop61efs.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/admins")
public interface AdminApi {

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> findAll();

    @GetMapping("/users/fullDetails")
    public ResponseEntity<List<User>> findAllFull();

    @GetMapping("/users/codes")
    public ResponseEntity<List<ConfirmationCode>> findAllCodes(@RequestParam String email);



}
