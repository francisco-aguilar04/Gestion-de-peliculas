package com.salesianos.dam.gestiondepeliculas.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesianos.dam.gestiondepeliculas.error.NoEncontrado.ActorNoEncontradoException;
import com.salesianos.dam.gestiondepeliculas.error.NoEncontrado.DirectorNoEncontradoException;
import com.salesianos.dam.gestiondepeliculas.error.NoEncontrado.PeliculaNoEncontradaException;
import jakarta.annotation.Nullable;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ElementKind;
import jakarta.validation.Path;
import lombok.Builder;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.List;

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

    @ExceptionHandler(PeliculaYaExistenteException.class)
    public ProblemDetail handlePeliculaYaExistente(PeliculaYaExistenteException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle("Ya existe una película con ese título");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://example.com/errors/conflict"));

        return problem;
    }

    @ExceptionHandler(ActorYaEnRepartoException.class)
    public ProblemDetail handleActorYaExistente(ActorYaEnRepartoException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle("El actor ya está en el reparto");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://example.com/errors/conflict"));

        return problem;
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail result = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, "Error de validación");

        List<ApiValidationSubError> subErrors =
                ex.getAllErrors().stream()
                        .map(ApiValidationSubError::from)
                        .toList();

        result.setProperty("invalid-params", subErrors);


        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result);
    }


    @Builder
    record ApiValidationSubError(
            String object,
            String message,
            @JsonInclude(JsonInclude.Include.NON_NULL)
            String field,
            @JsonInclude(JsonInclude.Include.NON_NULL)
            Object rejectedValue
    ) {

        public ApiValidationSubError(String object, String message) {
            this(object, message, null, null);
        }

        public static ApiValidationSubError from(ObjectError error) {

            ApiValidationSubError result = null;

            if (error instanceof FieldError fieldError) {
                result = ApiValidationSubError.builder()
                        .object(fieldError.getObjectName())
                        .message(fieldError.getDefaultMessage())
                        .field(fieldError.getField())
                        .rejectedValue(fieldError.getRejectedValue())
                        .build();
            } else {
                result = ApiValidationSubError.builder()
                        .object(error.getObjectName())
                        .message(error.getDefaultMessage())
                        .build();
            }

            return result;


        }

        public static ApiValidationSubError from(ConstraintViolation v) {
            return ApiValidationSubError.builder()
                    .message(v.getMessage())
                    .rejectedValue(v.getInvalidValue())
                    .object(v.getRootBeanClass().getSimpleName())
                    .field(getFieldName(v))
                    .build();
        }

        private static String getFieldName(ConstraintViolation<?> v) {

            String field = null;

            for (Path.Node node : v.getPropertyPath()) {
                if (node.getKind() == ElementKind.PROPERTY || node.getKind() == ElementKind.PARAMETER) {
                    field = node.getName() != null ? node.getName() : node.toString();
                }
            }
            return field;
        }

    }
}
