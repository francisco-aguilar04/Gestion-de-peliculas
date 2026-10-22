package com.salesianos.dam.gestiondepeliculas.service;

import com.salesianos.dam.gestiondepeliculas.dto.pelicula.EditPeliculaDto;
import com.salesianos.dam.gestiondepeliculas.error.ActorYaEnRepartoException;
import com.salesianos.dam.gestiondepeliculas.error.noEncontrado.ActorNoEncontradoException;
import com.salesianos.dam.gestiondepeliculas.error.noEncontrado.DirectorNoEncontradoException;
import com.salesianos.dam.gestiondepeliculas.error.noEncontrado.PeliculaNoEncontradaException;
import com.salesianos.dam.gestiondepeliculas.error.PeliculaYaExistenteException;
import com.salesianos.dam.gestiondepeliculas.model.Actor;
import com.salesianos.dam.gestiondepeliculas.model.Director;
import com.salesianos.dam.gestiondepeliculas.model.Pelicula;
import com.salesianos.dam.gestiondepeliculas.repository.ActorRepository;
import com.salesianos.dam.gestiondepeliculas.repository.DirectorRepository;
import com.salesianos.dam.gestiondepeliculas.repository.PeliculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;

    public Pelicula addPelicula(EditPeliculaDto editPeliculaDto) {

        if (peliculaRepository.existsByTitulo(editPeliculaDto.titulo())) {

            throw new PeliculaYaExistenteException(
                    "Ya existe una película con el título " + editPeliculaDto.titulo());

        }

        Director d = directorRepository.findById(editPeliculaDto.directorId())
                .orElseThrow(() -> new DirectorNoEncontradoException (
                        "No se encontró el director de id " + editPeliculaDto.directorId()));

        Pelicula p = editPeliculaDto.to(d);


        return peliculaRepository.save(p);

    }

    public Pelicula addActorToPelicula(Long peliculaId, Long actorId) {

        Pelicula p = getPeliculaById(peliculaId);

        Actor a = actorRepository.findById(actorId)
                .orElseThrow(() -> new ActorNoEncontradoException(
                        "No se encontró el actor de id " + actorId));

        if (p.getActores().contains(a)) {

            throw new ActorYaEnRepartoException(
                    "El actor de id " + actorId + " ya está en el reparto de la película");

        }

        p.getActores().add(a);

        return peliculaRepository.save(p);

    }

    public List<Pelicula> getAllPeliculas() {

        List<Pelicula> result = peliculaRepository.findAll();

        return result;
    }

    public Pelicula getPeliculaById(Long id) {

        return peliculaRepository.findById(id)
                .orElseThrow(() -> new PeliculaNoEncontradaException("No se encontró la película de id " + id));

    }

    public Pelicula updatePelicula(Long id, EditPeliculaDto editPeliculaDto) {

        return peliculaRepository.findById(id)
                .map(p -> {

                    String nuevoTitulo = editPeliculaDto.titulo();

                    if (!nuevoTitulo.equals(p.getTitulo()) && peliculaRepository.existsByTitulo(nuevoTitulo)) {

                        throw new PeliculaYaExistenteException("Ya existe una película con el título " + nuevoTitulo);

                    }

                    Optional.ofNullable(editPeliculaDto.titulo()).ifPresent(p::setTitulo);
                    Optional.ofNullable(editPeliculaDto.genero()).ifPresent(p::setGenero);
                    Optional.ofNullable(editPeliculaDto.fechaEstreno()).ifPresent(p::setFechaEstreno);
                    return peliculaRepository.save(p);
                })
                .orElseThrow(() -> new PeliculaNoEncontradaException("No se encontró la película de id " + id));
    }

    public void deletePelicula(Long id) {

        peliculaRepository.deleteById(id);
    }

}
