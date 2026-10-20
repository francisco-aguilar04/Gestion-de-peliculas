package com.salesianos.dam.gestiondepeliculas.controller;

import com.salesianos.dam.gestiondepeliculas.dto.director.EditDirectorDto;
import com.salesianos.dam.gestiondepeliculas.dto.director.GetDirectorDetail;
import com.salesianos.dam.gestiondepeliculas.model.Director;
import com.salesianos.dam.gestiondepeliculas.service.DirectorService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<GetDirectorDetail>> getAllDirectores() {

        List<Director> result = directorService.getAllDirectores();
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetDirectorDetail::of)
                        .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetDirectorDetail> getDirectorById(@PathVariable @Min(value = 1) Long id) {

        return ResponseEntity.ok(GetDirectorDetail.of(directorService.getDirectorById(id)));

    }

}
