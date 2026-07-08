package com.ipd.Gestion_Scolaire.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Demande d'inscription d'un étudiant à un cours")
public class InscriptionRequest {

    @NotNull(message = "L'etudiant est obligatoire")
    @Schema(description = "ID de l'étudiant", example = "1")
    private Long etudiantId;

    @NotNull(message = "Le cours est obligatoire")
    @Schema(description = "ID du cours", example = "1")
    private Long coursId;
}