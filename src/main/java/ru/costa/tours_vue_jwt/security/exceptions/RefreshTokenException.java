package ru.costa.tours_vue_jwt.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class RefreshTokenException extends Throwable{

    public RefreshTokenException(String token, String message) {
        super("Fail: Refresh Token: %s, message %s".formatted(token, message));
    }
}
