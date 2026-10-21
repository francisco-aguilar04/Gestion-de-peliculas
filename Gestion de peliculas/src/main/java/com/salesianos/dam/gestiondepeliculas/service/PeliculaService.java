package com.salesianos.dam.gestiondepeliculas.service;

import com.salesianos.dam.gestiondepeliculas.dto.pelicula.EditPeliculaDto;
import com.salesianos.dam.gestiondepeliculas.model.Actor;
import com.salesianos.dam.gestiondepeliculas.model.Director;
import com.salesianos.dam.gestiondepeliculas.model.Pelicula;
import com.salesianos.dam.gestiondepeliculas.repository.ActorRepository;
import com.salesianos.dam.gestiondepeliculas.repository.DirectorRepository;
import com.salesianos.dam.gestiondepeliculas.repository.PeliculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;

    public Pelicula addPelicula(EditPeliculaDto editPeliculaDto) {

        Director d = directorRepository.getReferenceById(editPeliculaDto.directorId());
        Pelicula p = editPeliculaDto.to(d);

        return peliculaRepository.save(p);

    }

    public Pelicula addActorToPelicula(Long peliculaId, Long actorId) {

        Pelicula p = peliculaRepository.getReferenceById(peliculaId);
        Actor a = actorRepository.getReferenceById(actorId);

        p.getActores().add(a);

        return peliculaRepository.save(p);

    }

    public List<Pelicula> getAllPeliculas() {

        List<Pelicula> result = peliculaRepository.findAll();

        return result;
    }

    public Pelicula getPeliculaById(Long id) {

        return peliculaRepository.findById(id).orElse(null);

    }

}
