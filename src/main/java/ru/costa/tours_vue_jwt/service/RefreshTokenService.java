package ru.costa.tours_vue_jwt.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.costa.tours_vue_jwt.repository.RefreshTokenRepository;
import ru.costa.tours_vue_jwt.security.entity.RefreshToken;
import ru.costa.tours_vue_jwt.security.exceptions.RefreshTokenException;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${jwt.refresh_token_duration}")
    private Long refreshTokenDuration;
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken(String username) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userService.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User %s not found.".formatted(username))));
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDuration));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyRefreshToken(RefreshToken token) throws RefreshTokenException {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenException(token.getToken(), "Refresh token was expired. Please sign in!");
        }
        return token;
    }

    @Transactional
    public int deleteRefreshToken(Long id) {
        return refreshTokenRepository.deleteByUser(userService.getUserById(id));
    }
}
