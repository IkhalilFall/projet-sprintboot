package com.ipd.Gestion_Scolaire.controller;

import com.ipd.Gestion_Scolaire.dto.EnseignantRequest;
import com.ipd.Gestion_Scolaire.dto.EnseignantResponse;
import com.ipd.Gestion_Scolaire.service.EnseignantService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/enseignants")
@RequiredArgsConstructor
@Tag(name = "Enseignants", description = "Endpoints pour la gestion des enseignants")
@SecurityRequirement(name = "Bearer Authentication")
public class EnseignantController {

    private final EnseignantService enseignantService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Creer un nouvel enseignant",
            description = "Cree un nouvel enregistrement d'enseignant (Admin seulement)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EnseignantRequest.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Enseignant exemple",
                                            summary = "Payload de creation d'un enseignant",
                                            value = """
                                                    {
                                                      "nom": "Martin",
                                                      "prenom": "Pierre",
                                                      "email": "pierre.martin@example.com",
                                                      "specialite": "Mathematiques"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Enseignant cree avec succes",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnseignantResponse.class))),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "403", description = "Acces refuse")
    })
    public EnseignantResponse create(@Valid @RequestBody EnseignantRequest request) {
        return enseignantService.create(request);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT')")
    @Operation(summary = "Recuperer tous les enseignants", description = "Retourne la liste complete des enseignants")
    @ApiResponse(
            responseCode = "200",
            description = "Liste des enseignants",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EnseignantResponse.class)))
    )
    public List<EnseignantResponse> findAll() {
        return enseignantService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT')")
    @Operation(summary = "Recuperer un enseignant par ID", description = "Retourne les details d'un enseignant specifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Enseignant trouve",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnseignantResponse.class))),
            @ApiResponse(responseCode = "404", description = "Enseignant non trouve")
    })
    public EnseignantResponse findById(@Parameter(description = "ID de l'enseignant") @PathVariable Long id) {
        return enseignantService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Mettre a jour un enseignant",
            description = "Modifie les informations d'un enseignant (Admin seulement)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EnseignantRequest.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Enseignant exemple",
                                            summary = "Payload de mise a jour d'un enseignant",
                                            value = """
                                                    {
                                                      "nom": "Martin",
                                                      "prenom": "Pierre",
                                                      "email": "pierre.martin@example.com",
                                                      "specialite": "Mathematiques"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Enseignant mis a jour",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnseignantResponse.class))),
            @ApiResponse(responseCode = "404", description = "Enseignant non trouve"),
            @ApiResponse(responseCode = "403", description = "Acces refuse")
    })
    public EnseignantResponse update(@Parameter(description = "ID de l'enseignant") @PathVariable Long id, @Valid @RequestBody EnseignantRequest request) {
        return enseignantService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un enseignant", description = "Supprime un enseignant du systeme (Admin seulement)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Enseignant supprime"),
            @ApiResponse(responseCode = "404", description = "Enseignant non trouve"),
            @ApiResponse(responseCode = "403", description = "Acces refuse")
    })
    public void delete(@Parameter(description = "ID de l'enseignant") @PathVariable Long id) {
        enseignantService.delete(id);
    }
}
