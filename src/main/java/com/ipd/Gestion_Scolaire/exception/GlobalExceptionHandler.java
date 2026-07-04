package com.ipd.Gestion_Scolaire.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 - Données invalides
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Erreur de validation");
        problem.setDetail("Les données envoyées sont invalides");
        problem.setType(URI.create("/errors/validation"));
        return problem;
    }

    // 404 - Ressource introuvable
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleNotFound(ResourceNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Ressource Non Trouvee");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("/errors/not-found"));
        return problem;
    }

    // 500 - Erreur inattendue
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGlobal(Exception ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problem.setTitle("Erreur Interne");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("/errors/internal"));
        return problem;
    }
}