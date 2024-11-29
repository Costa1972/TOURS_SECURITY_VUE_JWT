package ru.costa.tours_vue_jwt.security.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class JwtResponse {
    private String accessToken;
    private String tokenType;
    private String username;
    private Set<String> roles;

    public JwtResponse(String accessToken, String tokenType, String username, Set<String> roles) {
        this.tokenType = "Bearer";
        this.accessToken = accessToken;
        this.username = username;
        this.roles = roles;
    }
}
