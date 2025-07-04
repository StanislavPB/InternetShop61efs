package net.internetshop61efs.service.exception;


import org.springframework.security.core.AuthenticationException;

public class InvalidJwtException extends AuthenticationException {
    public InvalidJwtException(String message) {
        super(message);
    }
}
