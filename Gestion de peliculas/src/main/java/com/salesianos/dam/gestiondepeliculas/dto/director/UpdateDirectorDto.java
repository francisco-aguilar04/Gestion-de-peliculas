package com.salesianos.dam.gestiondepeliculas.dto.director;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(
        name = "UpdateDirectorDto",
        description = "Datos para actualizar un director. Todos los campos son opcionales: solo se modifican los que se envían"
)
public record UpdateDirectorDto(

        @Schema(description = "Nuevo nombre del director (máximo 100 caracteres)", example = "Christopher Nolan")
        @Pattern(regexp = ".*\\S.*", message = "El nombre no puede estar vacío")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String name,

        @Schema(description = "Nuevo año de nacimiento (mínimo 1850)", example = "1970")
        @Min(value = 1850, message = "El año de nacimiento no es válido")
        Integer anioNacimiento

) {
}