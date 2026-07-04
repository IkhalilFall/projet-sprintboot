package com.ipd.Gestion_Scolaire.repository;

import com.ipd.Gestion_Scolaire.entity.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {
}