package com.ipd.Gestion_Scolaire.service;

import com.ipd.Gestion_Scolaire.dto.AuthResponse;
import com.ipd.Gestion_Scolaire.dto.LoginRequest;
import com.ipd.Gestion_Scolaire.dto.RefreshTokenRequest;
import com.ipd.Gestion_Scolaire.dto.RegisterRequest;
import com.ipd.Gestion_Scolaire.entity.RefreshToken;
import com.ipd.Gestion_Scolaire.entity.Role;
import com.ipd.Gestion_Scolaire.entity.User;
import com.ipd.Gestion_Scolaire.exception.ConflictException;
import com.ipd.Gestion_Scolaire.repository.RefreshTokenRepository;
import com.ipd.Gestion_Scolaire.repository.UserRepository;
import com.ipd.Gestion_Scolaire.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Locale;
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
        String email = normalizeEmail(request.getEmail());
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException("Utilisateur déjà existant");
        }

        if (!StringUtils.hasText(request.getPassword())) {
            throw new IllegalArgumentException("Le mot de passe est obligatoire");
        }

        Role role;
        try {
            role = Role.valueOf(requireText(request.getRole(), "Le rôle est obligatoire").toUpperCase(Locale.ROOT));
        } catch (Exception ex) {
            throw new IllegalArgumentException("Rôle utilisateur invalide");
        }

        User user = User.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(email)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
        userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(user.getEmail());
        String refreshToken = createRefreshToken(user);
        return new AuthResponse(accessToken, refreshToken, "Bearer");
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        String email = normalizeEmail(request.getEmail());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Identifiants invalides"));

        if (!passwordEncoder.matches(requireText(request.getPassword(), "Le mot de passe est obligatoire"), user.getPassword())) {
            throw new BadCredentialsException("Identifiants invalides");
        }

        String accessToken = jwtService.generateAccessToken(user.getEmail());
        String refreshToken = createRefreshToken(user);
        return new AuthResponse(accessToken, refreshToken, "Bearer");
    }

    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(requireText(request.getRefreshToken(), "Le refresh token est obligatoire"))
                .orElseThrow(() -> new BadCredentialsException("Refresh token invalide"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new BadCredentialsException("Refresh token expiré");
        }

        User user = refreshToken.getUser();
        String newAccessToken = jwtService.generateAccessToken(user.getEmail());
        return new AuthResponse(newAccessToken, refreshToken.getToken(), "Bearer");
    }

    @Transactional
    private String createRefreshToken(User user) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByUser(user)
                .orElse(new RefreshToken());

        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(
                Instant.now().plusMillis(refreshTokenExpiration)
        );

        refreshTokenRepository.save(refreshToken);

        return refreshToken.getToken();
    }

    private String normalizeEmail(String email) {
        return requireText(email, "L'email est obligatoire").toLowerCase(Locale.ROOT);
    }

    private String requireText(String value, String message) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }
}
