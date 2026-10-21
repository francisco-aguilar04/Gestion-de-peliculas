package com.salesianos.dam.gestiondepeliculas.error;

import com.salesianos.dam.gestiondepeliculas.error.NoEncontrado.ActorNoEncontradoException;
import com.salesianos.dam.gestiondepeliculas.error.NoEncontrado.DirectorNoEncontradoException;
import com.salesianos.dam.gestiondepeliculas.error.NoEncontrado.PeliculaNoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PeliculaNoEncontradaException.class)
    public ProblemDetail handlePeliculaNoEncontrada(PeliculaNoEncontradaException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("No se ha encontrado la película");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://example.com/errors/not-found"));

        return problem;
    }

    @ExceptionHandler(DirectorNoEncontradoException.class)
    public ProblemDetail handleDirectorNoEncontrado(DirectorNoEncontradoException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("No se ha encontrado al director");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://example.com/errors/not-found"));

        return problem;
    }

    @ExceptionHandler(ActorNoEncontradoException.class)
    public ProblemDetail handleActorNoEncontrado(ActorNoEncontradoException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("No se ha encontrado al actor");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://example.com/errors/not-found"));

        return problem;
    }

}
