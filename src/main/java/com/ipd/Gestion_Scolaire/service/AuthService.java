package com.ipd.Gestion_Scolaire.service;

import com.ipd.Gestion_Scolaire.dto.AuthResponse;
import com.ipd.Gestion_Scolaire.dto.LoginRequest;
import com.ipd.Gestion_Scolaire.dto.RefreshTokenRequest;
import com.ipd.Gestion_Scolaire.dto.RegisterRequest;
import com.ipd.Gestion_Scolaire.entity.RefreshToken;
import com.ipd.Gestion_Scolaire.entity.Role;
import com.ipd.Gestion_Scolaire.entity.User;
import com.ipd.Gestion_Scolaire.repository.RefreshTokenRepository;
import com.ipd.Gestion_Scolaire.repository.UserRepository;
import com.ipd.Gestion_Scolaire.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .build();
        userRepository.save(user);
        String accessToken = jwtService.generateAccessToken(user.getEmail());
        String refreshToken = createRefreshToken(user);
        return new AuthResponse(accessToken, refreshToken, "Bearer");
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email introuvable"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect");
        }
        String accessToken = jwtService.generateAccessToken(user.getEmail());
        String refreshToken = createRefreshToken(user);
        return new AuthResponse(accessToken, refreshToken, "Bearer");
    }

    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Refresh token invalide"));
        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expiré");
        }
        User user = refreshToken.getUser();
        String newAccessToken = jwtService.generateAccessToken(user.getEmail());
        return new AuthResponse(newAccessToken, refreshToken.getToken(), "Bearer");
    }

    @Transactional
    private String createRefreshToken(User user) {
        refreshTokenRepository.deleteByUser(user);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiration));
        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }
}