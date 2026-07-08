package com.ipd.Gestion_Scolaire.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Erreur de validation");
        problem.setDetail("Les données envoyées sont invalides");
        problem.setType(URI.create("/errors/validation"));
        return problem;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleUnreadableBody(HttpMessageNotReadableException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Corps de requête invalide");
        problem.setDetail("Le corps de la requête est illisible ou incomplet");
        problem.setType(URI.create("/errors/request-body"));
        return problem;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Paramètre invalide");
        problem.setDetail("La valeur fournie pour le paramètre est invalide");
        problem.setType(URI.create("/errors/type-mismatch"));
        return problem;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ProblemDetail handleMissingParameter(MissingServletRequestParameterException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Paramètre manquant");
        problem.setDetail("Un paramètre obligatoire est manquant");
        problem.setType(URI.create("/errors/missing-parameter"));
        return problem;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleNotFound(ResourceNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Ressource non trouvée");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("/errors/not-found"));
        return problem;
    }

    @ExceptionHandler(ConflictException.class)
    public ProblemDetail handleConflict(ConflictException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle("Conflit");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("/errors/conflict"));
        return problem;
    }

    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ProblemDetail handleAuthentication(Exception ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problem.setTitle("Authentification échouée");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("/errors/authentication"));
        return problem;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Requête invalide");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("/errors/bad-request"));
        return problem;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrity(DataIntegrityViolationException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle("Conflit de données");
        problem.setDetail("Une contrainte d'unicité ou d'intégrité a été violée");
        problem.setType(URI.create("/errors/data-integrity"));
        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGlobal(Exception ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problem.setTitle("Erreur interne");
        problem.setDetail("Une erreur inattendue est survenue");
        problem.setType(URI.create("/errors/internal"));
        return problem;
    }
}
