package com.salesianos.dam.gestiondepeliculas.dto.director;

import com.salesianos.dam.gestiondepeliculas.model.Director;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "GetDirectorDetail",
        description = "Detalle de un director"
)
public record GetDirectorDetail(

        @Schema(
                description = "Identificador único del director",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Nombre del director",
                example = "Christopher Nolan"
        )
        String name,

        @Schema(
                description = "Año de nacimiento del director",
                example = "1970"
        )
        Integer anioNacimiento

) {

    public static GetDirectorDetail of(Director d) {

        return new GetDirectorDetail(
                d.getId(),
                d.getName(),
                d.getAnioNacimiento()
        );

    }

}