package com.salesianos.dam.gestiondepeliculas.controller;

import com.salesianos.dam.gestiondepeliculas.dto.pelicula.EditPeliculaDto;
import com.salesianos.dam.gestiondepeliculas.dto.pelicula.GetPeliculaDetail;
import com.salesianos.dam.gestiondepeliculas.model.Pelicula;
import com.salesianos.dam.gestiondepeliculas.service.PeliculaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/peliculas")
public class PeliculaController {

    private final PeliculaService peliculaService;

    @PostMapping
    public ResponseEntity<GetPeliculaDetail> addPelicula(
           @Valid @RequestBody EditPeliculaDto pelicula) {

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

    @PutMapping("/{id}")
    public ResponseEntity<GetPeliculaDetail> updatePelicula(
            @PathVariable Long id,
            @RequestBody EditPeliculaDto pelicula) {

        return ResponseEntity.ok(
                GetPeliculaDetail.of(peliculaService.updatePelicula(id, pelicula)));


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePelicula(@PathVariable Long id) {

        peliculaService.deletePelicula(id);
        return ResponseEntity.noContent().build();

    }

}
