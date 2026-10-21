package com.salesianos.dam.gestiondepeliculas.repository;

import com.salesianos.dam.gestiondepeliculas.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

    boolean existsByTitulo(String titulo);

}
