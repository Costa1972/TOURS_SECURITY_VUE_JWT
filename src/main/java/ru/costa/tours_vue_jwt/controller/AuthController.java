package ru.costa.tours_vue_jwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.costa.tours_vue_jwt.entity.User;
import ru.costa.tours_vue_jwt.security.entity.RefreshToken;
import ru.costa.tours_vue_jwt.security.entity.requests.JwtRequest;
import ru.costa.tours_vue_jwt.security.entity.respones.JwtResponse;
import ru.costa.tours_vue_jwt.security.utils.JwtUtil;
import ru.costa.tours_vue_jwt.service.RefreshTokenService;
import ru.costa.tours_vue_jwt.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.saveUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtUtil.generateToken(userDetails.getUsername());
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(jwtRequest.getUsername());
        Set<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        return ResponseEntity.ok().body(
                JwtResponse.builder()
                        .username(userDetails.getUsername())
                        .roles(roles)
                        .accessToken(accessToken)
                        .refreshToken(refreshToken.getToken())
                        .build()
        );
    }
}
