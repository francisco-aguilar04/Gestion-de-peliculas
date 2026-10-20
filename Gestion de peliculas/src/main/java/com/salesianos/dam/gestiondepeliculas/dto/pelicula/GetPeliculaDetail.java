package com.salesianos.dam.gestiondepeliculas.dto.pelicula;

import com.salesianos.dam.gestiondepeliculas.dto.actor.GetActorDetail;
import com.salesianos.dam.gestiondepeliculas.model.Pelicula;

import java.util.List;

public record GetPeliculaDetail(
        Long id,
        String titulo,
        String nombreDirector,
        List<GetActorDetail> actores
) {

    public static GetPeliculaDetail of(Pelicula p) {

        return new GetPeliculaDetail(
                p.getId(),
                p.getTitulo(),
                p.getDirector().getName(),
                p.getActores().stream()
                        .map(GetActorDetail::of)
                        .toList()
        );

    }

}
