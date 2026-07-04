package com.ipd.Gestion_Scolaire.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "cours")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private int credits;

    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;
}