package com.salesianos.dam.gestiondepeliculas.dto.director;

import com.salesianos.dam.gestiondepeliculas.model.Director;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(
        name = "EditDirectorDto",
        description = "Datos necesarios para crear o editar un director"
)
public record EditDirectorDto(

        @Schema(
                description = "Nombre del director (obligatorio, máximo 100 caracteres)",
                example = "Christopher Nolan"
        )
        @NotBlank(message = "El nombre del director es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String name,

        @Schema(
                description = "Año de nacimiento del director (obligatorio, mínimo 1850)",
                example = "1970"
        )
        @NotNull(message = "El año de nacimiento es obligatorio")
        @Min(value = 1850, message = "El año de nacimiento no es válido")
        Integer anioNacimiento

) {

    public Director to() {
        return Director.builder()
                .name(name)
                .anioNacimiento(anioNacimiento)
                .build();
    }

}