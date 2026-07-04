package com.ipd.Gestion_Scolaire.controller;

import com.ipd.Gestion_Scolaire.dto.InscriptionRequest;
import com.ipd.Gestion_Scolaire.dto.InscriptionResponse;
import com.ipd.Gestion_Scolaire.service.InscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/inscriptions")
@RequiredArgsConstructor
public class InscriptionController {

    private final InscriptionService inscriptionService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public InscriptionResponse create(@Valid @RequestBody InscriptionRequest request) {
        return inscriptionService.create(request);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT')")
    public List<InscriptionResponse> findAll() {
        return inscriptionService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT')")
    public InscriptionResponse findById(@PathVariable Long id) {
        return inscriptionService.findById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        inscriptionService.delete(id);
    }
}