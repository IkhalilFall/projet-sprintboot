package com.ipd.Gestion_Scolaire.service;

import com.ipd.Gestion_Scolaire.dto.InscriptionRequest;
import com.ipd.Gestion_Scolaire.dto.InscriptionResponse;
import java.util.List;

public interface InscriptionService {
    InscriptionResponse create(InscriptionRequest request);
    InscriptionResponse findById(Long id);
    List<InscriptionResponse> findAll();
    void delete(Long id);
}