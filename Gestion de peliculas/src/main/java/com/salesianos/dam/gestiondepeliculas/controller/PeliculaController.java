package com.salesianos.dam.gestiondepeliculas.controller;

import com.salesianos.dam.gestiondepeliculas.dto.pelicula.EditPeliculaDto;
import com.salesianos.dam.gestiondepeliculas.dto.pelicula.GetPeliculaDetail;
import com.salesianos.dam.gestiondepeliculas.service.PeliculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pelicula")
public class PeliculaController {

    private final PeliculaService peliculaService;

    @PostMapping
    public ResponseEntity<GetPeliculaDetail> addPelicula(
            @RequestBody EditPeliculaDto pelicula) {

        return ResponseEntity.status(201)
                .body(GetPeliculaDetail.of(peliculaService.addPelicula(pelicula)));

    }

}
