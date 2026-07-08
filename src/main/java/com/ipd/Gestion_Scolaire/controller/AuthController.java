package com.ipd.Gestion_Scolaire.controller;

import com.ipd.Gestion_Scolaire.dto.AuthResponse;
import com.ipd.Gestion_Scolaire.dto.LoginRequest;
import com.ipd.Gestion_Scolaire.dto.RefreshTokenRequest;
import com.ipd.Gestion_Scolaire.dto.RegisterRequest;
import com.ipd.Gestion_Scolaire.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints pour l'authentification et la gestion des tokens")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(
            summary = "Enregistrer un nouvel utilisateur",
            description = "Cree un compte utilisateur avec les informations fournies",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegisterRequest.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Inscription utilisateur",
                                            summary = "Payload de creation de compte",
                                            value = """
                                                    {
                                                      "nom": "Dupont",
                                                      "prenom": "Jean",
                                                      "email": "jean.dupont@example.com",
                                                      "password": "SecurePassword123!",
                                                      "role": "ETUDIANT"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscription reussie",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "409", description = "Utilisateur deja existant")
    })
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Connexion utilisateur",
            description = "Authentifie l'utilisateur et retourne les tokens JWT",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginRequest.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Connexion utilisateur",
                                            summary = "Payload de connexion",
                                            value = """
                                                    {
                                                      "email": "jean.dupont@example.com",
                                                      "password": "SecurePassword123!"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Connexion reussie",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Identifiants invalides"),
            @ApiResponse(responseCode = "401", description = "Non authentifie")
    })
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh-token")
    @Operation(
            summary = "Rafraichir le token",
            description = "Genere un nouveau JWT a partir du refresh token",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RefreshTokenRequest.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Refresh token",
                                            summary = "Payload de rafraichissement",
                                            value = """
                                                    {
                                                      "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token rafraichi avec succes",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Refresh token invalide ou expire")
    })
    public AuthResponse refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }
}
