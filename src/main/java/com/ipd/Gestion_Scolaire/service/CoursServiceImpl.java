package com.ipd.Gestion_Scolaire.service;

import com.ipd.Gestion_Scolaire.dto.CoursRequest;
import com.ipd.Gestion_Scolaire.dto.CoursResponse;
import com.ipd.Gestion_Scolaire.entity.Cours;
import com.ipd.Gestion_Scolaire.entity.Enseignant;
import com.ipd.Gestion_Scolaire.exception.ResourceNotFoundException;
import com.ipd.Gestion_Scolaire.repository.CoursRepository;
import com.ipd.Gestion_Scolaire.repository.EnseignantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoursServiceImpl implements CoursService {

    private final CoursRepository coursRepository;
    private final EnseignantRepository enseignantRepository;

    @Override
    public CoursResponse create(CoursRequest request) {
        Enseignant enseignant = enseignantRepository.findById(request.getEnseignantId())
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant introuvable"));
        Cours cours = new Cours();
        cours.setTitre(request.getTitre());
        cours.setDescription(request.getDescription());
        cours.setCredits(request.getCredits());
        cours.setEnseignant(enseignant);
        return toResponse(coursRepository.save(cours));
    }

    @Override
    public CoursResponse findById(Long id) {
        return toResponse(coursRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cours " + id + " introuvable")));
    }

    @Override
    public List<CoursResponse> findAll() {
        return coursRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CoursResponse update(Long id, CoursRequest request) {
        Cours existant = coursRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cours " + id + " introuvable"));
        Enseignant enseignant = enseignantRepository.findById(request.getEnseignantId())
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant introuvable"));
        existant.setTitre(request.getTitre());
        existant.setDescription(request.getDescription());
        existant.setCredits(request.getCredits());
        existant.setEnseignant(enseignant);
        return toResponse(coursRepository.save(existant));
    }

    @Override
    public void delete(Long id) {
        coursRepository.deleteById(id);
    }

    private CoursResponse toResponse(Cours c) {
        return new CoursResponse(
                c.getId(),
                c.getTitre(),
                c.getDescription(),
                c.getCredits(),
                c.getEnseignant().getNom(),
                c.getEnseignant().getPrenom()
        );
    }
}