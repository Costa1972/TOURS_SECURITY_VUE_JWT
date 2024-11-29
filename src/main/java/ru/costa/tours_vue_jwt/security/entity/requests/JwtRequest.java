package ru.costa.tours_vue_jwt.security.entity.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
