package net.internetshop61efs.controller;

import lombok.RequiredArgsConstructor;
import net.internetshop61efs.controller.api.AuthApi;
import net.internetshop61efs.security.dto.AuthRequest;
import net.internetshop61efs.security.dto.AuthResponse;
import net.internetshop61efs.security.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<AuthResponse> authenticate(AuthRequest request) {

        String jwt = authService.generateJwt(request);

        return new ResponseEntity<>(new AuthResponse(jwt), HttpStatus.OK);
    }
}
