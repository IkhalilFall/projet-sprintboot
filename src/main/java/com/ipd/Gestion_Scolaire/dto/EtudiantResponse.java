package com.ipd.Gestion_Scolaire.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Réponse contenant les informations d'un étudiant")
public class EtudiantResponse {
    
    @Schema(description = "Identifiant unique de l'étudiant", example = "1")
    private Long id;
    
    @Schema(description = "Nom de l'étudiant", example = "Dupont")
    private String nom;
    
    @Schema(description = "Prénom de l'étudiant", example = "Marie")
    private String prenom;
    
    @Schema(description = "Email de l'étudiant", example = "marie.dupont@example.com")
    private String email;
    
    @Schema(description = "Numéro de téléphone", example = "+33612345678")
    private String telephone;
    
    @Schema(description = "Numéro de matricule", example = "MAT202401001")
    private String matricule;
}