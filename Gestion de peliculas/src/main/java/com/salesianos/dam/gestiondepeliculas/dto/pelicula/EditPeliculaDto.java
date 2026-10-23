package com.salesianos.dam.gestiondepeliculas.dto.pelicula;

import com.salesianos.dam.gestiondepeliculas.model.Director;
import com.salesianos.dam.gestiondepeliculas.model.Pelicula;
import com.salesianos.dam.gestiondepeliculas.validacion.DirectorMayoriaEdad;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(
        name = "EditPeliculaDto",
        description = "Datos necesarios para crear o editar una película"
)
public record EditPeliculaDto(

        @Schema(
                description = "Título de la película (obligatorio, único, máximo 150 caracteres)",
                example = "Origen"
        )
        @NotBlank(message = "El título de la película es obligatorio")
        @Size(max = 150, message = "El título no puede superar los 150 caracteres")
        String titulo,

        @Schema(
                description = "Género de la película (obligatorio, máximo 50 caracteres)",
                example = "Ciencia ficción"
        )
        @NotBlank(message = "El género es obligatorio")
        @Size(max = 50, message = "El género no puede superar los 50 caracteres")
        String genero,

        @Schema(
                description = "Fecha de estreno de la película (formato yyyy-MM-dd)",
                example = "2010-07-16"
        )
        @NotNull(message = "La fecha de estreno es obligatoria")
        LocalDate fechaEstreno,

        @Schema(
                description = "Identificador del director de la película. Debe existir y ser mayor de edad",
                example = "1"
        )
        @DirectorMayoriaEdad(message = "El director debe ser mayor de edad para lanzar una película")
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