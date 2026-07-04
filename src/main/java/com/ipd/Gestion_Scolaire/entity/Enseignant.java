package com.ipd.Gestion_Scolaire.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "enseignants")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Enseignant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String email;
    private String specialite;
}