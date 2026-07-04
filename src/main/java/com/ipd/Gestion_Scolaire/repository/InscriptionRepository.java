package com.ipd.Gestion_Scolaire.repository;

import com.ipd.Gestion_Scolaire.entity.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
}