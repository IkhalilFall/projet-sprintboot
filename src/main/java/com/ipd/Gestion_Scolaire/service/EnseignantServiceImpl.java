package com.ipd.Gestion_Scolaire.service;

import com.ipd.Gestion_Scolaire.dto.EnseignantRequest;
import com.ipd.Gestion_Scolaire.dto.EnseignantResponse;
import com.ipd.Gestion_Scolaire.entity.Enseignant;
import com.ipd.Gestion_Scolaire.exception.ResourceNotFoundException;
import com.ipd.Gestion_Scolaire.repository.EnseignantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnseignantServiceImpl implements EnseignantService {

    private final EnseignantRepository repository;

    @Override
    public EnseignantResponse create(EnseignantRequest request) {
        Enseignant e = new Enseignant();
        e.setNom(request.getNom());
        e.setPrenom(request.getPrenom());
        e.setEmail(request.getEmail());
        e.setSpecialite(request.getSpecialite());
        return toResponse(repository.save(e));
    }

    @Override
    public EnseignantResponse findById(Long id) {
        return toResponse(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant " + id + " introuvable")));
    }

    @Override
    public List<EnseignantResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EnseignantResponse update(Long id, EnseignantRequest request) {
        Enseignant existant = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant " + id + " introuvable"));
        existant.setNom(request.getNom());
        existant.setPrenom(request.getPrenom());
        existant.setEmail(request.getEmail());
        existant.setSpecialite(request.getSpecialite());
        return toResponse(repository.save(existant));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private EnseignantResponse toResponse(Enseignant e) {
        return new EnseignantResponse(
                e.getId(),
                e.getNom(),
                e.getPrenom(),
                e.getEmail(),
                e.getSpecialite()
        );
    }
}