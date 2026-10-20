package com.salesianos.dam.gestiondepeliculas.dto.pelicula;

import com.salesianos.dam.gestiondepeliculas.dto.director.GetDirectorDetail;
import com.salesianos.dam.gestiondepeliculas.model.Director;
import com.salesianos.dam.gestiondepeliculas.model.Pelicula;

public record GetPeliculaDetail(
        Long id,
        String titulo,
        String nombreDirector
) {

    public static GetPeliculaDetail of(Pelicula p) {

        return new GetPeliculaDetail(
                p.getId(),
                p.getTitulo(),
                p.getDirector().getName()
        );

    }

}
