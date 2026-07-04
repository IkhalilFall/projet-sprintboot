package com.ipd.Gestion_Scolaire.service;

import com.ipd.Gestion_Scolaire.dto.EtudiantRequest;
import com.ipd.Gestion_Scolaire.dto.EtudiantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface EtudiantService {
    EtudiantResponse create(EtudiantRequest request);
    EtudiantResponse findById(Long id);
    List<EtudiantResponse> findAll();
    Page<EtudiantResponse> findAllPaginated(Pageable pageable);
    EtudiantResponse update(Long id, EtudiantRequest request);
    void delete(Long id);
}