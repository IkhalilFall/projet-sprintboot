package com.ipd.Gestion_Scolaire.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Schema(description = "Réponse contenant les informations d'une inscription")
public class InscriptionResponse {
    
    @Schema(description = "Identifiant unique de l'inscription", example = "1")
    private Long id;
    
    @Schema(description = "Date de l'inscription", example = "2024-01-15")
    private LocalDate dateInscription;
    
    @Schema(description = "Nom de l'étudiant inscrit", example = "Dupont")
    private String etudiantNom;
    
    @Schema(description = "Prénom de l'étudiant inscrit", example = "Marie")
    private String etudiantPrenom;
    
    @Schema(description = "Titre du cours auquel l'étudiant est inscrit", example = "Mathématiques Avancées")
    private String coursTitre;
}