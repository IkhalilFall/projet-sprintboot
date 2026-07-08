package com.ipd.Gestion_Scolaire.controller;

import com.ipd.Gestion_Scolaire.dto.InscriptionRequest;
import com.ipd.Gestion_Scolaire.dto.InscriptionResponse;
import com.ipd.Gestion_Scolaire.service.InscriptionService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/inscriptions")
@RequiredArgsConstructor
@Tag(name = "Inscriptions", description = "Endpoints pour la gestion des inscriptions")
@SecurityRequirement(name = "Bearer Authentication")
public class InscriptionController {

    private final InscriptionService inscriptionService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Creer une nouvelle inscription",
            description = "Cree une nouvelle inscription pour un etudiant a un cours (Admin seulement)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = InscriptionRequest.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Inscription exemple",
                                            summary = "Payload de creation d'une inscription",
                                            value = """
                                                    {
                                                      "etudiantId": 1,
                                                      "coursId": 1
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscription creee avec succes",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InscriptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "403", description = "Acces refuse")
    })
    public InscriptionResponse create(@Valid @RequestBody InscriptionRequest request) {
        return inscriptionService.create(request);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT')")
    @Operation(summary = "Recuperer toutes les inscriptions", description = "Retourne la liste complete des inscriptions")
    @ApiResponse(
            responseCode = "200",
            description = "Liste des inscriptions",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = InscriptionResponse.class)))
    )
    public List<InscriptionResponse> findAll() {
        return inscriptionService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENSEIGNANT')")
    @Operation(summary = "Recuperer une inscription par ID", description = "Retourne les details d'une inscription specifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscription trouvee",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InscriptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Inscription non trouvee")
    })
    public InscriptionResponse findById(@Parameter(description = "ID de l'inscription") @PathVariable Long id) {
        return inscriptionService.findById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer une inscription", description = "Supprime une inscription du systeme (Admin seulement)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscription supprimee"),
            @ApiResponse(responseCode = "404", description = "Inscription non trouvee"),
            @ApiResponse(responseCode = "403", description = "Acces refuse")
    })
    public void delete(@Parameter(description = "ID de l'inscription") @PathVariable Long id) {
        inscriptionService.delete(id);
    }
}
