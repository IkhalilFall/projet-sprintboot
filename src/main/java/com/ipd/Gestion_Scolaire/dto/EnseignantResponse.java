package com.ipd.Gestion_Scolaire.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Réponse contenant les informations d'un enseignant")
public class EnseignantResponse {
    
    @Schema(description = "Identifiant unique de l'enseignant", example = "1")
    private Long id;
    
    @Schema(description = "Nom de l'enseignant", example = "Martin")
    private String nom;
    
    @Schema(description = "Prénom de l'enseignant", example = "Pierre")
    private String prenom;
    
    @Schema(description = "Email de l'enseignant", example = "pierre.martin@example.com")
    private String email;
    
    @Schema(description = "Spécialité d'enseignement", example = "Mathématiques")
    private String specialite;
}