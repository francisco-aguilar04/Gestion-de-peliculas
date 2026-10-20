package com.salesianos.dam.gestiondepeliculas.dto.pelicula;

import com.salesianos.dam.gestiondepeliculas.model.Director;
import com.salesianos.dam.gestiondepeliculas.model.Pelicula;

import java.time.LocalDate;

public record EditPeliculaDto(
        String titulo,
        String genero,
        LocalDate fechaEstreno,
        Long directorId
) {

    public Pelicula to(Director director) {
        return Pelicula.builder()
                .titulo(titulo)
                .genero(genero)
                .fechaEstreno(fechaEstreno)
                .director(director)
                .build();
    }

}
