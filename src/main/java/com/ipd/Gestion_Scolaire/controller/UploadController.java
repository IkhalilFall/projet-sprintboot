package com.ipd.Gestion_Scolaire.controller;

import com.ipd.Gestion_Scolaire.entity.Etudiant;
import com.ipd.Gestion_Scolaire.exception.ResourceNotFoundException;
import com.ipd.Gestion_Scolaire.repository.EtudiantRepository;
import com.ipd.Gestion_Scolaire.service.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping("/etudiants")
public class UploadController {

    private final FileStorageService fileStorageService;
    private final EtudiantRepository etudiantRepository;

    public UploadController(FileStorageService fileStorageService, EtudiantRepository etudiantRepository) {
        this.fileStorageService = fileStorageService;
        this.etudiantRepository = etudiantRepository;
    }

    @PostMapping("/{id}/photo")
    @PreAuthorize("hasAnyRole('ADMIN','ETUDIANT')")
    public ResponseEntity<?> uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant introuvable"));

        String path = fileStorageService.storeFile(file, "photos", FileStorageService.IMAGE_TYPES);
        etudiant.setPhotoUrl(path);
        etudiantRepository.save(etudiant);

        return ResponseEntity.ok(Map.of("message", "Photo uploadée avec succès", "url", path));
    }

    @PostMapping("/{id}/docs")
    @PreAuthorize("hasAnyRole('ADMIN','ETUDIANT')")
    public ResponseEntity<?> uploadDoc(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant introuvable"));

        String path = fileStorageService.storeFile(file, "docs", FileStorageService.PDF_TYPES);
        etudiant.setDocumentUrl(path);
        etudiantRepository.save(etudiant);

        return ResponseEntity.ok(Map.of("message", "Document uploadé avec succès", "url", path));
    }

    @GetMapping("/{id}/photo")
    public ResponseEntity<Resource> getPhoto(@PathVariable Long id) throws MalformedURLException {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant introuvable"));
        Path path = Paths.get(fileStorageService.getUploadDir()).resolve(etudiant.getPhotoUrl());
        Resource resource = new UrlResource(path.toUri());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
    }

    @GetMapping("/{id}/docs")
    public ResponseEntity<Resource> getDoc(@PathVariable Long id) throws MalformedURLException {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant introuvable"));
        Path path = Paths.get(fileStorageService.getUploadDir()).resolve(etudiant.getDocumentUrl());
        Resource resource = new UrlResource(path.toUri());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(resource);
    }
}