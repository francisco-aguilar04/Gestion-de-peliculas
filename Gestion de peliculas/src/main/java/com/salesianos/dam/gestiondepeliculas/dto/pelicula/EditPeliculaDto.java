package com.salesianos.dam.gestiondepeliculas.dto.pelicula;

import com.salesianos.dam.gestiondepeliculas.model.Director;
import com.salesianos.dam.gestiondepeliculas.model.Pelicula;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EditPeliculaDto(

        @NotBlank(message = "El título de la película es obligatorio")
        @Size(max = 150, message = "El título no puede superar los 150 caracteres")
        String titulo,

        @NotBlank(message = "El género es obligatorio")
        @Size(max = 50, message = "El género no puede superar los 50 caracteres")
        String genero,

        @NotNull(message = "La fecha de estreno es obligatoria")
        LocalDate fechaEstreno,

        @NotNull(message = "El id del director es obligatorio")
        @Min(value = 1, message = "El id del director no es válido")
        Long directorId
) {

    public Pelicula to(Director director) {
        return Pelicula.builder()
                .titulo(titulo)
                .genero(genero)
                .fechaEstreno(fechaEstreno)
                .director(director)
                .build();
    }

}
