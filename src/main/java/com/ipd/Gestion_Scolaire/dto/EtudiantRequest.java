package com.ipd.Gestion_Scolaire.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Demande de création/modification d'un étudiant")
public class EtudiantRequest {

    @NotBlank(message = "Le nom est obligatoire")
    @Schema(description = "Nom de l'étudiant", example = "Dupont")
    private String nom;

    @NotBlank(message = "Le prenom est obligatoire")
    @Schema(description = "Prénom de l'étudiant", example = "Marie")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Email invalide")
    @Schema(description = "Email unique de l'étudiant", example = "marie.dupont@example.com")
    private String email;

    @NotBlank(message = "Le telephone est obligatoire")
    @Schema(description = "Numéro de téléphone de l'étudiant", example = "+33612345678")
    private String telephone;

    @NotBlank(message = "Le matricule est obligatoire")
    @Schema(description = "Numéro de matricule unique", example = "MAT202401001")
    private String matricule;
}