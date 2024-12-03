package ru.costa.tours_vue_jwt.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.costa.tours_vue_jwt.entity.Role;
import ru.costa.tours_vue_jwt.entity.User;
import ru.costa.tours_vue_jwt.repository.RoleRepository;
import ru.costa.tours_vue_jwt.security.entity.RefreshToken;
import ru.costa.tours_vue_jwt.security.entity.requests.JwtRequest;
import ru.costa.tours_vue_jwt.security.entity.respones.JwtResponse;
import ru.costa.tours_vue_jwt.security.entity.respones.MessageResponse;
import ru.costa.tours_vue_jwt.security.utils.JwtUtil;
import ru.costa.tours_vue_jwt.service.RefreshTokenService;
import ru.costa.tours_vue_jwt.service.RoleService;
import ru.costa.tours_vue_jwt.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody @Valid User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username is required."));
        }
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username already exists."));
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Passwords do not match."));
        }
        Set<Role> roles = user.getRoles();
        {
            roles.forEach(role -> {
                if (role.getRoleName().equals("ROLE_ADMIN")) {
                    Role adminRole = roleService.getRole("ROLE_ADMIN");
                    roles.add(adminRole);
                    user.setRoles(Set.of(adminRole));
                } else {
                    Role userRole = roleService.getRole("ROLE_USER");
                    user.setRoles(Set.of(userRole));
                }

            });
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody JwtRequest jwtRequest) {
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
