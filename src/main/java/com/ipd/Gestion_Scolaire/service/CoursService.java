package com.ipd.Gestion_Scolaire.service;

import com.ipd.Gestion_Scolaire.dto.CoursRequest;
import com.ipd.Gestion_Scolaire.dto.CoursResponse;
import java.util.List;

public interface CoursService {
    CoursResponse create(CoursRequest request);
    CoursResponse findById(Long id);
    List<CoursResponse> findAll();
    CoursResponse update(Long id, CoursRequest request);
    void delete(Long id);
}