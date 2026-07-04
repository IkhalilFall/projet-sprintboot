package com.ipd.Gestion_Scolaire.repository;

import com.ipd.Gestion_Scolaire.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
}