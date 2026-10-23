package com.salesianos.dam.gestiondepeliculas.dto.actor;

import com.salesianos.dam.gestiondepeliculas.model.Actor;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "GetActorDetail",
        description = "Detalle de un actor"
)
public record GetActorDetail(

        @Schema(
                description = "Identificador único del actor",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Nombre del actor",
                example = "Leonardo DiCaprio"
        )
        String name

) {

    public static GetActorDetail of(Actor a) {

        return new GetActorDetail(
                a.getId(),
                a.getName()
        );

    }

}