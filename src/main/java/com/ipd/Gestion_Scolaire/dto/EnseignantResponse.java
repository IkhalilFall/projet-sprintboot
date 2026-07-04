package com.ipd.Gestion_Scolaire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnseignantResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String specialite;
}