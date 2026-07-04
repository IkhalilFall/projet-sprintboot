package com.ipd.Gestion_Scolaire.controller;

import com.ipd.Gestion_Scolaire.dto.EtudiantRequest;
import com.ipd.Gestion_Scolaire.dto.EtudiantResponse;
import com.ipd.Gestion_Scolaire.service.EtudiantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/etudiants")
@RequiredArgsConstructor
public class EtudiantController {

    private final EtudiantService etudiantService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public EtudiantResponse create(@Valid @RequestBody EtudiantRequest request) {
        return etudiantService.create(request);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT')")
    public List<EtudiantResponse> findAll() {
        return etudiantService.findAll();
    }

    @GetMapping("/paginated")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT')")
    public Page<EtudiantResponse> findAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return etudiantService.findAllPaginated(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT')")
    public EtudiantResponse findById(@PathVariable Long id) {
        return etudiantService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EtudiantResponse update(@PathVariable Long id, @Valid @RequestBody EtudiantRequest request) {
        return etudiantService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        etudiantService.delete(id);
    }
}