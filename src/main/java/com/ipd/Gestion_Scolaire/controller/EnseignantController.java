package com.ipd.Gestion_Scolaire.controller;

import com.ipd.Gestion_Scolaire.dto.EnseignantRequest;
import com.ipd.Gestion_Scolaire.dto.EnseignantResponse;
import com.ipd.Gestion_Scolaire.service.EnseignantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/enseignants")
@RequiredArgsConstructor
public class EnseignantController {

    private final EnseignantService enseignantService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public EnseignantResponse create(@Valid @RequestBody EnseignantRequest request) {
        return enseignantService.create(request);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT')")
    public List<EnseignantResponse> findAll() {
        return enseignantService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT')")
    public EnseignantResponse findById(@PathVariable Long id) {
        return enseignantService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EnseignantResponse update(@PathVariable Long id, @Valid @RequestBody EnseignantRequest request) {
        return enseignantService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        enseignantService.delete(id);
    }
}