package com.ipd.Gestion_Scolaire.repository;

import com.ipd.Gestion_Scolaire.entity.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
}