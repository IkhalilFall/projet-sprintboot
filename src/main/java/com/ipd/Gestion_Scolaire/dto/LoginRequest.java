package com.ipd.Gestion_Scolaire.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Demande de connexion utilisateur")
public class LoginRequest {

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Email invalide")
    @Schema(description = "Email de l'utilisateur", example = "jean.dupont@example.com")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Schema(description = "Mot de passe de l'utilisateur", example = "SecurePassword123!")
    private String password;
}
