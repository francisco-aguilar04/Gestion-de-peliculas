package com.salesianos.dam.gestiondepeliculas.service;

import com.salesianos.dam.gestiondepeliculas.dto.director.EditDirectorDto;
import com.salesianos.dam.gestiondepeliculas.error.DirectorConPeliculasException;
import com.salesianos.dam.gestiondepeliculas.error.noEncontrado.DirectorNoEncontradoException;
import com.salesianos.dam.gestiondepeliculas.model.Director;
import com.salesianos.dam.gestiondepeliculas.repository.DirectorRepository;
import com.salesianos.dam.gestiondepeliculas.repository.PeliculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;
    private final PeliculaRepository peliculaRepository;

    public Director addDirector(EditDirectorDto editDirectorDto) {

        Director d = editDirectorDto.to();

        return directorRepository.save(d);
    }

    public List<Director> getAllDirectores() {

        List<Director> result = directorRepository.findAll();

        if (result.isEmpty()) {

            throw new DirectorNoEncontradoException("No existen registros de ningún director.");

        }

        return result;
    }

    public Director getDirectorById(Long id) {

        return directorRepository.findById(id)
                .orElseThrow(() -> new DirectorNoEncontradoException("No se encontró el director de id " + id));

    }

    public Director updateDirector(Long id, EditDirectorDto editDirectorDto) {

        return directorRepository.findById(id)
                .map(d -> {
                    Optional.ofNullable(editDirectorDto.name()).ifPresent(d::setName);
                    Optional.ofNullable(editDirectorDto.anioNacimiento()).ifPresent(d::setAnioNacimiento);
                    return directorRepository.save(d);
                }).orElseThrow(() -> new DirectorNoEncontradoException("No se encontró el director de id " + id));

    }

    public void deleteDirector(Long id) {

        if (peliculaRepository.existsByDirectorId(id)) {

            throw new DirectorConPeliculasException(
                    "No se puede borrar el director de id " + id + " porque está asociado a una o más películas.");

        }

        directorRepository.deleteById(id);
    }

}
