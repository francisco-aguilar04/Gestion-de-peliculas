package com.salesianos.dam.gestiondepeliculas.dto.director;

import com.salesianos.dam.gestiondepeliculas.model.Director;

public record GetDirectorDetail(

        Long id,
        String name,
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
