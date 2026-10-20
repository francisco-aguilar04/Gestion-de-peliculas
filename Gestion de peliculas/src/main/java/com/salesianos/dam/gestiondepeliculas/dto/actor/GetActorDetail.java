package com.salesianos.dam.gestiondepeliculas.dto.actor;

import com.salesianos.dam.gestiondepeliculas.model.Actor;

public record GetActorDetail(

        Long id,
        String name

) {

    public static GetActorDetail of(Actor a) {

        return new GetActorDetail(
                a.getId(),
                a.getName()
        );

    }

}
