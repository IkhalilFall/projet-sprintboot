package com.ipd.Gestion_Scolaire.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CoursRequest {

    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @Min(value = 1, message = "Les credits doivent etre positifs")
    private int credits;

    @NotNull(message = "L'enseignant est obligatoire")
    private Long enseignantId;
}