package com.ipd.Gestion_Scolaire.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Demande d'enregistrement d'un nouvel utilisateur")
public class RegisterRequest {

    @NotBlank(message = "Le nom est obligatoire")
    @Schema(description = "Nom de l'utilisateur", example = "Dupont")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Schema(description = "Prénom de l'utilisateur", example = "Jean")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Email invalide")
    @Schema(description = "Email unique de l'utilisateur", example = "jean.dupont@example.com")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Schema(description = "Mot de passe de l'utilisateur", example = "SecurePassword123!")
    private String password;

    @NotBlank(message = "Le rôle est obligatoire")
    @Schema(description = "Rôle de l'utilisateur (ADMIN, ENSEIGNANT, ETUDIANT)", example = "ETUDIANT")
    private String role;
}
