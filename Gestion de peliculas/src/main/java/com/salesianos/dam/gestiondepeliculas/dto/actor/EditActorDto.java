package com.salesianos.dam.gestiondepeliculas.dto.actor;

import com.salesianos.dam.gestiondepeliculas.model.Actor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(
        name = "EditActorDto",
        description = "Datos necesarios para crear o editar un actor"
)
public record EditActorDto(

        @Schema(
                description = "Nombre del actor (obligatorio, máximo 100 caracteres)",
                example = "Leonardo DiCaprio"
        )
        @NotBlank(message = "El nombre del actor es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String name
) {

    public Actor to() {
        return Actor.builder()
                .name(name)
                .build();
    }

}