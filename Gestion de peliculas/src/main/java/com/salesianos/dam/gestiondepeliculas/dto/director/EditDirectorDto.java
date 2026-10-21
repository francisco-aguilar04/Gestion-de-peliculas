package com.salesianos.dam.gestiondepeliculas.dto.director;

import com.salesianos.dam.gestiondepeliculas.model.Director;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EditDirectorDto(

        @NotBlank(message = "El nombre del director es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String name,

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
