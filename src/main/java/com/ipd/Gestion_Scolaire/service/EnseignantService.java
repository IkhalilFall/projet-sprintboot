package com.ipd.Gestion_Scolaire.service;

import com.ipd.Gestion_Scolaire.dto.EnseignantRequest;
import com.ipd.Gestion_Scolaire.dto.EnseignantResponse;
import java.util.List;

public interface EnseignantService {
    EnseignantResponse create(EnseignantRequest request);
    EnseignantResponse findById(Long id);
    List<EnseignantResponse> findAll();
    EnseignantResponse update(Long id, EnseignantRequest request);
    void delete(Long id);
}