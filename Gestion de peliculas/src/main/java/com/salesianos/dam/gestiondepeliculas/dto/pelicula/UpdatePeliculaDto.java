package com.salesianos.dam.gestiondepeliculas.dto.pelicula;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(
        name = "UpdatePeliculaDto",
        description = "Datos para actualizar una película. Todos los campos son opcionales: solo se modifican los que se envían"
)
public record UpdatePeliculaDto(

        @Schema(description = "Nuevo título (único, máximo 150 caracteres)", example = "Origen")
        @Pattern(regexp = ".*\\S.*", message = "El título no puede estar vacío")
        @Size(max = 150, message = "El título no puede superar los 150 caracteres")
        String titulo,

        @Schema(description = "Nuevo género (máximo 50 caracteres)", example = "Ciencia ficción")
        @Pattern(regexp = ".*\\S.*", message = "El género no puede estar vacío")
        @Size(max = 50, message = "El género no puede superar los 50 caracteres")
        String genero,

        @Schema(description = "Nueva fecha de estreno (formato yyyy-MM-dd)", example = "2010-07-16")
        LocalDate fechaEstreno
) {
}