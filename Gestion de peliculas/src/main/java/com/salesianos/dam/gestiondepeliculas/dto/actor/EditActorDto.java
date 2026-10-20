package com.salesianos.dam.gestiondepeliculas.dto.actor;

import com.salesianos.dam.gestiondepeliculas.model.Actor;

public record EditActorDto(

        String name
) {

    public Actor to() {
        return Actor.builder()
                .name(name)
                .build();
    }

}
