package com.salesianos.dam.gestiondepeliculas.controller;

import com.salesianos.dam.gestiondepeliculas.dto.actor.EditActorDto;
import com.salesianos.dam.gestiondepeliculas.dto.actor.GetActorDetail;
import com.salesianos.dam.gestiondepeliculas.model.Actor;
import com.salesianos.dam.gestiondepeliculas.service.ActorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/actor")
@Tag(name = "Actor Controller", description = "Gestión del reparto de actores")
public class ActorController {

    private final ActorService actorService;

    @Operation(
            summary = "Crear un actor",
            description = "Permite dar de alta un nuevo actor o actriz en el catálogo"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Actor creado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetActorDetail.class),
                            examples = @ExampleObject(
                                    name = "Actor creado",
                                    value = """
                                            {
                                              "id": 1,
                                              "name": "Ivana Baquero"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada no válidos",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 400",
                                    summary = "Nombre del actor vacío",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Bad Request",
                                              "status": 400,
                                              "detail": "Error de validación",
                                              "instance": "/actor",
                                              "invalid-params": [
                                                {
                                                  "object": "editActorDto",
                                                  "message": "El nombre del actor es obligatorio",
                                                  "field": "name",
                                                  "rejectedValue": ""
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<GetActorDetail> addActor(
            @Valid @RequestBody EditActorDto actor) {

        return ResponseEntity.status(201)
                .body(GetActorDetail.of(actorService.addActor(actor)));

    }

    @Operation(
            summary = "Obtener todos los actores",
            description = "Permite obtener el listado de todos los actores del catálogo"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de actores",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = GetActorDetail.class)
                            ),
                            examples = @ExampleObject(
                                    name = "Listado de actores",
                                    value = """
                                            [
                                              {
                                                "id": 1,
                                                "name": "Ivana Baquero"
                                              },
                                              {
                                                "id": 2,
                                                "name": "Sergi López"
                                              }
                                            ]
                                            """
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<GetActorDetail>> getAllActores() {

        List<Actor> result = actorService.getAllActores();
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetActorDetail::of)
                        .toList());
    }

    @Operation(
            summary = "Obtener un actor por su id",
            description = "Permite obtener los datos de un actor a partir de su identificador"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Actor encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetActorDetail.class),
                            examples = @ExampleObject(
                                    name = "Actor encontrado",
                                    value = """
                                            {
                                              "id": 1,
                                              "name": "Ivana Baquero"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "El id no es válido",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un actor con ese id",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 404",
                                    summary = "Actor no encontrado",
                                    value = """
                                            {
                                              "type": "https://example.com/errors/not-found",
                                              "title": "No se ha encontrado al actor",
                                              "status": 404,
                                              "detail": "No se encontró al actor de id 99",
                                              "instance": "/actor/99"
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetActorDetail> getActorById(@PathVariable @Min(value = 1) Long id) {

        return ResponseEntity.ok(GetActorDetail.of(actorService.getActorById(id)));

    }

}