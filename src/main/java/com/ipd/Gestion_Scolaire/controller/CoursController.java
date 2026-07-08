package com.ipd.Gestion_Scolaire.controller;

import com.ipd.Gestion_Scolaire.dto.CoursRequest;
import com.ipd.Gestion_Scolaire.dto.CoursResponse;
import com.ipd.Gestion_Scolaire.service.CoursService;
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
@RequestMapping("/cours")
@RequiredArgsConstructor
@Tag(name = "Cours", description = "Endpoints pour la gestion des cours")
@SecurityRequirement(name = "Bearer Authentication")
public class CoursController {

    private final CoursService coursService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Creer un nouveau cours",
            description = "Cree un nouvel enregistrement de cours (Admin seulement)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CoursRequest.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Cours exemple",
                                            summary = "Payload de creation d'un cours",
                                            value = """
                                                    {
                                                      "titre": "Mathematiques Avancees",
                                                      "description": "Cours de calcul differentiel et integral",
                                                      "credits": 3,
                                                      "enseignantId": 1
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cours cree avec succes",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CoursResponse.class))),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "403", description = "Acces refuse")
    })
    public CoursResponse create(@Valid @RequestBody CoursRequest request) {
        return coursService.create(request);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT') or hasRole('ETUDIANT')")
    @Operation(summary = "Recuperer tous les cours", description = "Retourne la liste complete des cours")
    @ApiResponse(
            responseCode = "200",
            description = "Liste des cours",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CoursResponse.class)))
    )
    public List<CoursResponse> findAll() {
        return coursService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT') or hasRole('ETUDIANT')")
    @Operation(summary = "Recuperer un cours par ID", description = "Retourne les details d'un cours specifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cours trouve",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CoursResponse.class))),
            @ApiResponse(responseCode = "404", description = "Cours non trouve")
    })
    public CoursResponse findById(@Parameter(description = "ID du cours") @PathVariable Long id) {
        return coursService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Mettre a jour un cours",
            description = "Modifie les informations d'un cours (Admin seulement)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CoursRequest.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Cours exemple",
                                            summary = "Payload de mise a jour d'un cours",
                                            value = """
                                                    {
                                                      "titre": "Mathematiques Avancees",
                                                      "description": "Cours de calcul differentiel et integral",
                                                      "credits": 3,
                                                      "enseignantId": 1
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cours mis a jour",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CoursResponse.class))),
            @ApiResponse(responseCode = "404", description = "Cours non trouve"),
            @ApiResponse(responseCode = "403", description = "Acces refuse")
    })
    public CoursResponse update(@Parameter(description = "ID du cours") @PathVariable Long id, @Valid @RequestBody CoursRequest request) {
        return coursService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un cours", description = "Supprime un cours du systeme (Admin seulement)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cours supprime"),
            @ApiResponse(responseCode = "404", description = "Cours non trouve"),
            @ApiResponse(responseCode = "403", description = "Acces refuse")
    })
    public void delete(@Parameter(description = "ID du cours") @PathVariable Long id) {
        coursService.delete(id);
    }
}
