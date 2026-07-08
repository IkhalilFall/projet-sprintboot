package com.ipd.Gestion_Scolaire.controller;

import com.ipd.Gestion_Scolaire.dto.EtudiantRequest;
import com.ipd.Gestion_Scolaire.dto.EtudiantResponse;
import com.ipd.Gestion_Scolaire.service.EtudiantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/etudiants")
@RequiredArgsConstructor
@Tag(name = "Etudiants", description = "Endpoints pour la gestion des etudiants")
@SecurityRequirement(name = "Bearer Authentication")
public class EtudiantController {

    private final EtudiantService etudiantService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Creer un nouvel etudiant",
            description = "Cree un nouvel enregistrement d'etudiant (Admin seulement)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EtudiantRequest.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Etudiant exemple",
                                            summary = "Payload de creation d'un etudiant",
                                            value = """
                                                    {
                                                      "nom": "Dupont",
                                                      "prenom": "Marie",
                                                      "email": "marie.dupont@example.com",
                                                      "telephone": "+33612345678",
                                                      "matricule": "MAT202401001"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Etudiant cree avec succes",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponse.class))),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "403", description = "Acces refuse")
    })
    public EtudiantResponse create(@Valid @RequestBody EtudiantRequest request) {
        return etudiantService.create(request);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT')")
    @Operation(summary = "Recuperer tous les etudiants", description = "Retourne la liste complete des etudiants")
    @ApiResponse(
            responseCode = "200",
            description = "Liste des etudiants",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EtudiantResponse.class)))
    )
    public List<EtudiantResponse> findAll() {
        return etudiantService.findAll();
    }

    @GetMapping("/paginated")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT')")
    @Operation(summary = "Recuperer les etudiants avec pagination", description = "Retourne les etudiants pagines")
    @ApiResponse(responseCode = "200", description = "Page d'etudiants", content = @Content(mediaType = "application/json"))
    public Page<EtudiantResponse> findAllPaginated(
            @Parameter(description = "Numero de la page (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de la page", example = "5")
            @RequestParam(defaultValue = "5") int size) {
        return etudiantService.findAllPaginated(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT')")
    @Operation(summary = "Recuperer un etudiant par ID", description = "Retourne les details d'un etudiant specifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Etudiant trouve",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponse.class))),
            @ApiResponse(responseCode = "404", description = "Etudiant non trouve")
    })
    public EtudiantResponse findById(@Parameter(description = "ID de l'etudiant") @PathVariable Long id) {
        return etudiantService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Mettre a jour un etudiant",
            description = "Modifie les informations d'un etudiant (Admin seulement)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EtudiantRequest.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Etudiant exemple",
                                            summary = "Payload de mise a jour d'un etudiant",
                                            value = """
                                                    {
                                                      "nom": "Dupont",
                                                      "prenom": "Marie",
                                                      "email": "marie.dupont@example.com",
                                                      "telephone": "+33612345678",
                                                      "matricule": "MAT202401001"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Etudiant mis a jour",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponse.class))),
            @ApiResponse(responseCode = "404", description = "Etudiant non trouve"),
            @ApiResponse(responseCode = "403", description = "Acces refuse")
    })
    public EtudiantResponse update(@Parameter(description = "ID de l'etudiant") @PathVariable Long id, @Valid @RequestBody EtudiantRequest request) {
        return etudiantService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un etudiant", description = "Supprime un etudiant du systeme (Admin seulement)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Etudiant supprime"),
            @ApiResponse(responseCode = "404", description = "Etudiant non trouve"),
            @ApiResponse(responseCode = "403", description = "Acces refuse")
    })
    public void delete(@Parameter(description = "ID de l'etudiant") @PathVariable Long id) {
        etudiantService.delete(id);
    }
}
