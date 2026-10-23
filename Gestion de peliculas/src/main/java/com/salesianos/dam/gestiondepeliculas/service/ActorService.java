package com.salesianos.dam.gestiondepeliculas.service;

import com.salesianos.dam.gestiondepeliculas.dto.actor.EditActorDto;
import com.salesianos.dam.gestiondepeliculas.error.noEncontrado.ActorNoEncontradoException;
import com.salesianos.dam.gestiondepeliculas.model.Actor;
import com.salesianos.dam.gestiondepeliculas.repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;

    public Actor addActor(EditActorDto editActorDto) {

        Actor a = editActorDto.to();

        return actorRepository.save(a);
    }

    public List<Actor> getAllActores() {

        List<Actor> result = actorRepository.findAll();

        if (result.isEmpty()) {

            throw new ActorNoEncontradoException("No existen registros de ningún actor.");

        }

        return result;
    }

    public Actor getActorById(Long id) {

        return actorRepository.findById(id)
                .orElseThrow(() -> new ActorNoEncontradoException("No se encontró al actor de id " + id));

    }
}
