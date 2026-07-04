package com.ipd.Gestion_Scolaire.service;

import com.ipd.Gestion_Scolaire.dto.InscriptionRequest;
import com.ipd.Gestion_Scolaire.dto.InscriptionResponse;
import com.ipd.Gestion_Scolaire.entity.Cours;
import com.ipd.Gestion_Scolaire.entity.Etudiant;
import com.ipd.Gestion_Scolaire.entity.Inscription;
import com.ipd.Gestion_Scolaire.exception.ResourceNotFoundException;
import com.ipd.Gestion_Scolaire.repository.CoursRepository;
import com.ipd.Gestion_Scolaire.repository.EtudiantRepository;
import com.ipd.Gestion_Scolaire.repository.InscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InscriptionServiceImpl implements InscriptionService {

    private final InscriptionRepository inscriptionRepository;
    private final EtudiantRepository etudiantRepository;
    private final CoursRepository coursRepository;

    @Override
    public InscriptionResponse create(InscriptionRequest request) {
        Etudiant etudiant = etudiantRepository.findById(request.getEtudiantId())
                .orElseThrow(() -> new ResourceNotFoundException("Etudiant introuvable"));
        Cours cours = coursRepository.findById(request.getCoursId())
                .orElseThrow(() -> new ResourceNotFoundException("Cours introuvable"));
        Inscription inscription = new Inscription();
        inscription.setEtudiant(etudiant);
        inscription.setCours(cours);
        inscription.setDateInscription(LocalDate.now());
        return toResponse(inscriptionRepository.save(inscription));
    }

    @Override
    public InscriptionResponse findById(Long id) {
        return toResponse(inscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscription " + id + " introuvable")));
    }

    @Override
    public List<InscriptionResponse> findAll() {
        return inscriptionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        inscriptionRepository.deleteById(id);
    }

    private InscriptionResponse toResponse(Inscription i) {
        return new InscriptionResponse(
                i.getId(),
                i.getDateInscription(),
                i.getEtudiant().getNom(),
                i.getEtudiant().getPrenom(),
                i.getCours().getTitre()
        );
    }
}