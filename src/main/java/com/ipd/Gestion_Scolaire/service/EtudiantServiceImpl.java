package com.ipd.Gestion_Scolaire.service;

import com.ipd.Gestion_Scolaire.dto.EtudiantRequest;
import com.ipd.Gestion_Scolaire.dto.EtudiantResponse;
import com.ipd.Gestion_Scolaire.entity.Etudiant;
import com.ipd.Gestion_Scolaire.exception.ResourceNotFoundException;
import com.ipd.Gestion_Scolaire.repository.EtudiantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EtudiantServiceImpl implements EtudiantService {

    private final EtudiantRepository repository;

    @Override
    public EtudiantResponse create(EtudiantRequest request) {
        Etudiant e = new Etudiant();
        e.setNom(request.getNom());
        e.setPrenom(request.getPrenom());
        e.setEmail(request.getEmail());
        e.setTelephone(request.getTelephone());
        e.setMatricule(request.getMatricule());
        return toResponse(repository.save(e));
    }

    @Override
    public EtudiantResponse findById(Long id) {
        return toResponse(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Etudiant " + id + " introuvable")));
    }

    @Override
    public List<EtudiantResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<EtudiantResponse> findAllPaginated(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::toResponse);
    }

    @Override
    public EtudiantResponse update(Long id, EtudiantRequest request) {
        Etudiant existant = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Etudiant " + id + " introuvable"));
        existant.setNom(request.getNom());
        existant.setPrenom(request.getPrenom());
        existant.setEmail(request.getEmail());
        existant.setTelephone(request.getTelephone());
        existant.setMatricule(request.getMatricule());
        return toResponse(repository.save(existant));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private EtudiantResponse toResponse(Etudiant e) {
        return new EtudiantResponse(
                e.getId(),
                e.getNom(),
                e.getPrenom(),
                e.getEmail(),
                e.getTelephone(),
                e.getMatricule()
        );
    }
}