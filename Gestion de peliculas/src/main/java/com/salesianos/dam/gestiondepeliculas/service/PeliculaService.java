package com.salesianos.dam.gestiondepeliculas.service;

import com.salesianos.dam.gestiondepeliculas.dto.pelicula.EditPeliculaDto;
import com.salesianos.dam.gestiondepeliculas.model.Director;
import com.salesianos.dam.gestiondepeliculas.model.Pelicula;
import com.salesianos.dam.gestiondepeliculas.repository.DirectorRepository;
import com.salesianos.dam.gestiondepeliculas.repository.PeliculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;
    private final DirectorRepository directorRepository;

    public Pelicula addPelicula(EditPeliculaDto editPeliculaDto) {

        Director d = directorRepository.getReferenceById(editPeliculaDto.directorId());
        Pelicula p = editPeliculaDto.to(d);

        return peliculaRepository.save(p);

    }

}
