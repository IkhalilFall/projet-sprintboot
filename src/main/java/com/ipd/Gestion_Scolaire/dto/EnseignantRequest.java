package com.ipd.Gestion_Scolaire.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Demande de création/modification d'un enseignant")
public class EnseignantRequest {

    @NotBlank(message = "Le nom est obligatoire")
    @Schema(description = "Nom de l'enseignant", example = "Martin")
    private String nom;

    @NotBlank(message = "Le prenom est obligatoire")
    @Schema(description = "Prénom de l'enseignant", example = "Pierre")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Email invalide")
    @Schema(description = "Email unique de l'enseignant", example = "pierre.martin@example.com")
    private String email;

    @NotBlank(message = "La specialite est obligatoire")
    @Schema(description = "Spécialité d'enseignement", example = "Mathématiques")
    private String specialite;
}