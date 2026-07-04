package com.ipd.Gestion_Scolaire.controller;

import com.ipd.Gestion_Scolaire.dto.CoursRequest;
import com.ipd.Gestion_Scolaire.dto.CoursResponse;
import com.ipd.Gestion_Scolaire.service.CoursService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cours")
@RequiredArgsConstructor
public class CoursController {

    private final CoursService coursService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CoursResponse create(@Valid @RequestBody CoursRequest request) {
        return coursService.create(request);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT') or hasRole('ETUDIANT')")
    public List<CoursResponse> findAll() {
        return coursService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT') or hasRole('ETUDIANT')")
    public CoursResponse findById(@PathVariable Long id) {
        return coursService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CoursResponse update(@PathVariable Long id, @Valid @RequestBody CoursRequest request) {
        return coursService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        coursService.delete(id);
    }
}