package com.ipd.Gestion_Scolaire.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Réponse contenant les informations d'un cours")
public class CoursResponse {
    
    @Schema(description = "Identifiant unique du cours", example = "1")
    private Long id;
    
    @Schema(description = "Titre du cours", example = "Mathématiques Avancées")
    private String titre;
    
    @Schema(description = "Description du cours", example = "Cours de calcul différentiel et intégral")
    private String description;
    
    @Schema(description = "Nombre de crédits", example = "3")
    private int credits;
    
    @Schema(description = "Nom de l'enseignant responsable", example = "Martin")
    private String enseignantNom;
    
    @Schema(description = "Prénom de l'enseignant responsable", example = "Pierre")
    private String enseignantPrenom;
}