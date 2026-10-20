package com.salesianos.dam.gestiondepeliculas.controller;

import com.salesianos.dam.gestiondepeliculas.dto.actor.EditActorDto;
import com.salesianos.dam.gestiondepeliculas.dto.actor.GetActorDetail;
import com.salesianos.dam.gestiondepeliculas.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
