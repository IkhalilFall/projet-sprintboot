package com.ipd.Gestion_Scolaire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class InscriptionResponse {
    private Long id;
    private LocalDate dateInscription;
    private String etudiantNom;
    private String etudiantPrenom;
    private String coursTitre;
}