package ru.costa.tours_vue_jwt.security.entity.requests;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RefreshTokenRequest {
    @NonNull
    private String refreshToken;
}
