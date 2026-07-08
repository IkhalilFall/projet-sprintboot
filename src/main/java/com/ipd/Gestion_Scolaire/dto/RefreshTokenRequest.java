package com.ipd.Gestion_Scolaire.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Demande de rafraîchissement de token")
public class RefreshTokenRequest {

    @NotBlank(message = "Le refresh token est obligatoire")
    @Schema(description = "Token de rafraîchissement valide", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String refreshToken;
}
