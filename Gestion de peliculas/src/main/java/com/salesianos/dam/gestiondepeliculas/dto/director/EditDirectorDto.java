package com.salesianos.dam.gestiondepeliculas.dto.director;

import com.salesianos.dam.gestiondepeliculas.model.Director;

public record EditDirectorDto(

        String name,
        Integer anioNacimiento
) {

    public Director to() {
        return Director.builder()
                .name(name)
                .anioNacimiento(anioNacimiento)
                .build();
    }

}
