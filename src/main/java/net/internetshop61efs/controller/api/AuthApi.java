package net.internetshop61efs.controller.api;

import jakarta.validation.Valid;
import net.internetshop61efs.security.dto.AuthRequest;
import net.internetshop61efs.security.dto.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/auth")
public interface AuthApi {
    @PostMapping
    ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request);

}
