package com.salesianos.dam.gestiondepeliculas.controller;

import com.salesianos.dam.gestiondepeliculas.dto.director.EditDirectorDto;
import com.salesianos.dam.gestiondepeliculas.dto.director.GetDirectorDetail;
import com.salesianos.dam.gestiondepeliculas.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/director")
public class DirectorController {

    private final DirectorService directorService;

    @PostMapping
    public ResponseEntity<GetDirectorDetail> addDirector(
            @RequestBody EditDirectorDto director) {

        return ResponseEntity.status(201)
                .body(GetDirectorDetail.of(directorService.addDirector(director)));

    }


}
