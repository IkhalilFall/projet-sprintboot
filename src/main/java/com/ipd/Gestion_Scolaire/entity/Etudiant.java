package com.ipd.Gestion_Scolaire.entity;


import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "etudiants")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String matricule;
    private String photoUrl;
    private String documentUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
