package net.internetshop61efs.controller;

import lombok.RequiredArgsConstructor;
import net.internetshop61efs.controller.api.UserApi;
import net.internetshop61efs.dto.UserResponseDto;
import net.internetshop61efs.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController implements UserApi {

    private final UserService service;



    //*найти пользователя по ID

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Integer id){
        return ResponseEntity.ok(service.findUserById(id));
    };

    //*найти пользователя по email
    @GetMapping()
    public ResponseEntity<UserResponseDto> findUserByEmail(@RequestParam String email){
        return ResponseEntity.ok(service.findUserByEmail(email));
    };


}
