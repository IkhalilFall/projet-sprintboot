package com.ipd.Gestion_Scolaire.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Demande de création/modification d'un cours")
public class CoursRequest {

    @NotBlank(message = "Le titre est obligatoire")
    @Schema(description = "Titre du cours", example = "Mathématiques Avancées")
    private String titre;

    @NotBlank(message = "La description est obligatoire")
    @Schema(description = "Description détaillée du cours", example = "Cours de calcul différentiel et intégral")
    private String description;

    @Min(value = 1, message = "Les credits doivent etre positifs")
    @Schema(description = "Nombre de crédits", example = "3")
    private int credits;

    @NotNull(message = "L'enseignant est obligatoire")
    @Schema(description = "ID de l'enseignant responsable", example = "1")
    private Long enseignantId;
}