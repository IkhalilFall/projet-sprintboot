package com.ipd.Gestion_Scolaire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoursResponse {
    private Long id;
    private String titre;
    private String description;
    private int credits;
    private String enseignantNom;
    private String enseignantPrenom;
}