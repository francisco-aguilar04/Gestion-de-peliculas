package com.salesianos.dam.gestiondepeliculas.dto.director;

import com.salesianos.dam.gestiondepeliculas.model.Director;

public record GetDirectorSimple(

        Long id,
        String name

) {

    public static GetDirectorSimple of(Director d) {

        return new GetDirectorSimple(
                d.getId(),
                d.getName()
        );

    }

}