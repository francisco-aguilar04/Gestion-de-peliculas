package com.salesianos.dam.gestiondepeliculas.controller;

import com.salesianos.dam.gestiondepeliculas.dto.actor.EditActorDto;
import com.salesianos.dam.gestiondepeliculas.dto.actor.GetActorDetail;
import com.salesianos.dam.gestiondepeliculas.dto.director.GetDirectorDetail;
import com.salesianos.dam.gestiondepeliculas.model.Actor;
import com.salesianos.dam.gestiondepeliculas.model.Director;
import com.salesianos.dam.gestiondepeliculas.service.ActorService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/actor")
public class ActorController {

    private final ActorService actorService;

    @PostMapping
    public ResponseEntity<GetActorDetail> addActor(
            @RequestBody EditActorDto actor) {

        return ResponseEntity.status(201)
                .body(GetActorDetail.of(actorService.addActor(actor)));

    }

    @GetMapping
    public ResponseEntity<List<GetActorDetail>> getAllActores() {

        List<Actor> result = actorService.getAllActores();
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetActorDetail::of)
                        .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetActorDetail> getDirectorById(@PathVariable @Min(value = 1) Long id) {

        return ResponseEntity.ok(GetActorDetail.of(actorService.getActorById(id)));

    }

}
