package com.salesianos.dam.gestiondepeliculas.dto.pelicula;

import com.salesianos.dam.gestiondepeliculas.dto.actor.GetActorDetail;
import com.salesianos.dam.gestiondepeliculas.dto.director.GetDirectorSimple;
import com.salesianos.dam.gestiondepeliculas.model.Pelicula;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(
        name = "GetPeliculaDetail",
        description = "Detalle de una película, con su director y su reparto"
)
public record GetPeliculaDetail(

        @Schema(
                description = "Identificador único de la película",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Título de la película",
                example = "Origen"
        )
        String titulo,

        @Schema(
                description = "Director de la película"
        )
        GetDirectorSimple director,

        @Schema(
                description = "Lista de actores que forman el reparto de la película"
        )
        List<GetActorDetail> actores
) {

    public static GetPeliculaDetail of(Pelicula p) {

        return new GetPeliculaDetail(
                p.getId(),
                p.getTitulo(),
                GetDirectorSimple.of(p.getDirector()),
                p.getActores().stream()
                        .map(GetActorDetail::of)
                        .toList()
        );

    }

}