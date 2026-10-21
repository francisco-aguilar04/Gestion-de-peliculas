package com.salesianos.dam.gestiondepeliculas.controller;

import com.salesianos.dam.gestiondepeliculas.dto.director.GetDirectorDetail;
import com.salesianos.dam.gestiondepeliculas.dto.pelicula.EditPeliculaDto;
import com.salesianos.dam.gestiondepeliculas.dto.pelicula.GetPeliculaDetail;
import com.salesianos.dam.gestiondepeliculas.model.Director;
import com.salesianos.dam.gestiondepeliculas.model.Pelicula;
import com.salesianos.dam.gestiondepeliculas.service.PeliculaService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/peliculas")
public class PeliculaController {

    private final PeliculaService peliculaService;

    @PostMapping
    public ResponseEntity<GetPeliculaDetail> addPelicula(
            @RequestBody EditPeliculaDto pelicula) {

        return ResponseEntity.status(201)
                .body(GetPeliculaDetail.of(peliculaService.addPelicula(pelicula)));

    }

    @PostMapping("/{peliculaId}/actores/{actorId}")
    public GetPeliculaDetail addActorToPelicula(
            @PathVariable Long peliculaId,
            @PathVariable Long actorId) {

        return GetPeliculaDetail.of(
                peliculaService.addActorToPelicula(peliculaId, actorId));

    }

    @GetMapping
    public ResponseEntity<List<GetPeliculaDetail>> getAllPeliculas() {

        List<Pelicula> result = peliculaService.getAllPeliculas();
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetPeliculaDetail::of)
                        .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPeliculaDetail> getPeliculaById(@PathVariable @Min(value = 1) Long id) {

        return ResponseEntity.ok(GetPeliculaDetail.of(peliculaService.getPeliculaById(id)));

    }

}
