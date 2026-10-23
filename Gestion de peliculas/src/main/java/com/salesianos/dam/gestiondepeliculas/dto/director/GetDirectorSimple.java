package com.salesianos.dam.gestiondepeliculas.dto.director;

import com.salesianos.dam.gestiondepeliculas.model.Director;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "GetDirectorSimple",
        description = "Información resumida de un director"
)
public record GetDirectorSimple(

        @Schema(
                description = "Identificador único del director",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Nombre del director",
                example = "Christopher Nolan"
        )
        String name

) {

    public static GetDirectorSimple of(Director d) {

        return new GetDirectorSimple(
                d.getId(),
                d.getName()
        );

    }

}