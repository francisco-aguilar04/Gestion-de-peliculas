package com.salesianos.dam.gestiondepeliculas.dto.actor;

import com.salesianos.dam.gestiondepeliculas.model.Actor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EditActorDto(

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
