package com.salesianos.dam.gestiondepeliculas.controller;

import com.salesianos.dam.gestiondepeliculas.dto.pelicula.EditPeliculaDto;
import com.salesianos.dam.gestiondepeliculas.dto.pelicula.GetPeliculaDetail;
import com.salesianos.dam.gestiondepeliculas.dto.pelicula.UpdatePeliculaDto;
import com.salesianos.dam.gestiondepeliculas.model.Pelicula;
import com.salesianos.dam.gestiondepeliculas.service.PeliculaService;
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
@RequestMapping("/api/v1/peliculas")
@Tag(name = "Película Controller", description = "Gestión de las películas y de su reparto de actores")
public class PeliculaController {

    private final PeliculaService peliculaService;

    @Operation(
            summary = "Crear una película",
            description = "Permite dar de alta una película. Requiere el id de un director existente, "
                    + "que debe ser mayor de edad, y un título que no esté repetido"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Película creada correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetPeliculaDetail.class),
                            examples = @ExampleObject(
                                    name = "Película creada",
                                    value = """
                                            {
                                              "id": 1,
                                              "titulo": "El laberinto del fauno",
                                              "director": {
                                                "id": 1,
                                                "name": "Guillermo del Toro"
                                              },
                                              "actores": []
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
                                    summary = "Director menor de edad",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Bad Request",
                                              "status": 400,
                                              "detail": "Error de validación",
                                              "instance": "/api/v1/peliculas",
                                              "invalid-params": [
                                                {
                                                  "object": "editPeliculaDto",
                                                  "message": "El director debe ser mayor de edad para lanzar una película",
                                                  "field": "directorId",
                                                  "rejectedValue": 5
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un director con el id indicado",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 404",
                                    summary = "Director no encontrado",
                                    value = """
                                            {
                                              "type": "https://example.com/errors/not-found",
                                              "title": "No se ha encontrado al director",
                                              "status": 404,
                                              "detail": "No se encontró el director de id 99",
                                              "instance": "/api/v1/peliculas"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Ya existe una película con ese título",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 409",
                                    summary = "Título duplicado",
                                    value = """
                                            {
                                              "type": "https://example.com/errors/conflict",
                                              "title": "Ya existe una película con ese título",
                                              "status": 409,
                                              "detail": "Ya existe una película con el título El laberinto del fauno",
                                              "instance": "/api/v1/peliculas"
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<GetPeliculaDetail> addPelicula(
            @Valid @RequestBody EditPeliculaDto pelicula) {

        return ResponseEntity.status(201)
                .body(GetPeliculaDetail.of(peliculaService.addPelicula(pelicula)));

    }

    @Operation(
            summary = "Asignar un actor a una película",
            description = "Añade un actor existente al reparto de una película existente (relación M:M). "
                    + "Un mismo actor no puede asignarse dos veces a la misma película"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Actor asignado correctamente. Devuelve la película con su reparto actualizado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetPeliculaDetail.class),
                            examples = @ExampleObject(
                                    name = "Reparto actualizado",
                                    value = """
                                            {
                                              "id": 1,
                                              "titulo": "El laberinto del fauno",
                                              "director": {
                                                "id": 1,
                                                "name": "Guillermo del Toro"
                                              },
                                              "actores": [
                                                {
                                                  "id": 1,
                                                  "name": "Ivana Baquero"
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe la película o el actor indicado",
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
                                              "detail": "No se encontró el actor de id 99",
                                              "instance": "/api/v1/peliculas/1/actores/99"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "El actor ya está en el reparto de la película",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 409",
                                    summary = "Actor ya en el reparto",
                                    value = """
                                            {
                                              "type": "https://example.com/errors/conflict",
                                              "title": "El actor ya está en el reparto",
                                              "status": 409,
                                              "detail": "El actor de id 1 ya está en el reparto de la película",
                                              "instance": "/api/v1/peliculas/1/actores/1"
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping("/{peliculaId}/actores/{actorId}")
    public GetPeliculaDetail addActorToPelicula(
            @PathVariable Long peliculaId,
            @PathVariable Long actorId) {

        return GetPeliculaDetail.of(
                peliculaService.addActorToPelicula(peliculaId, actorId));

    }

    @Operation(
            summary = "Obtener todas las películas",
            description = "Permite obtener el listado de todas las películas, con su director y su reparto"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de películas",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = GetPeliculaDetail.class)
                            ),
                            examples = @ExampleObject(
                                    name = "Listado de películas",
                                    value = """
                                            [
                                              {
                                                "id": 1,
                                                "titulo": "El laberinto del fauno",
                                                "director": {
                                                  "id": 1,
                                                  "name": "Guillermo del Toro"
                                                },
                                                "actores": [
                                                  {
                                                    "id": 1,
                                                    "name": "Ivana Baquero"
                                                  }
                                                ]
                                              },
                                              {
                                                "id": 2,
                                                "titulo": "Batman Begins",
                                                "director": {
                                                  "id": 2,
                                                  "name": "Christopher Nolan"
                                                },
                                                "actores": []
                                              }
                                            ]
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No hay ninguna película registrada",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 404",
                                    summary = "Sin películas",
                                    value = """
                                            {
                                              "type": "https://example.com/errors/not-found",
                                              "title": "No se ha encontrado la película",
                                              "status": 404,
                                              "detail": "No existen registros de ninguna película.",
                                              "instance": "/api/v1/peliculas"
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<GetPeliculaDetail>> getAllPeliculas() {

        List<Pelicula> result = peliculaService.getAllPeliculas();
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetPeliculaDetail::of)
                        .toList());
    }

    @Operation(
            summary = "Obtener una película por su id",
            description = "Permite obtener una película a partir de su identificador, "
                    + "incluyendo su director y la lista de su reparto de actores"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Película encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetPeliculaDetail.class),
                            examples = @ExampleObject(
                                    name = "Película encontrada",
                                    value = """
                                            {
                                              "id": 1,
                                              "titulo": "El laberinto del fauno",
                                              "director": {
                                                "id": 1,
                                                "name": "Guillermo del Toro"
                                              },
                                              "actores": [
                                                {
                                                  "id": 1,
                                                  "name": "Ivana Baquero"
                                                }
                                              ]
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
                    description = "No existe una película con ese id",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 404",
                                    summary = "Película no encontrada",
                                    value = """
                                            {
                                              "type": "https://example.com/errors/not-found",
                                              "title": "No se ha encontrado la película",
                                              "status": 404,
                                              "detail": "No se encontró la película de id 99",
                                              "instance": "/api/v1/peliculas/99"
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetPeliculaDetail> getPeliculaById(@PathVariable @Min(value = 1) Long id) {

        return ResponseEntity.ok(GetPeliculaDetail.of(peliculaService.getPeliculaById(id)));

    }

    @Operation(
            summary = "Actualizar una película",
            description = "Permite actualizar el título, el género y/o la fecha de estreno de una película. "
                    + "Solo se modifican los campos que se envían. El título no puede coincidir con el de otra película"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Película actualizada correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetPeliculaDetail.class),
                            examples = @ExampleObject(
                                    name = "Película actualizada",
                                    value = """
                                        {
                                          "id": 1,
                                          "titulo": "La mazmorra del fauno",
                                          "director": {
                                            "id": 1,
                                            "name": "Guillermo del Toro"
                                          },
                                          "actores": []
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
                                    summary = "Título vacío",
                                    value = """
                                        {
                                          "type": "about:blank",
                                          "title": "Bad Request",
                                          "status": 400,
                                          "detail": "Error de validación",
                                          "instance": "/api/v1/peliculas/1",
                                          "invalid-params": [
                                            {
                                              "object": "updatePeliculaDto",
                                              "message": "El título no puede estar vacío",
                                              "field": "titulo",
                                              "rejectedValue": ""
                                            }
                                          ]
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe una película con ese id",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 404",
                                    summary = "Película no encontrada",
                                    value = """
                                        {
                                          "type": "https://example.com/errors/not-found",
                                          "title": "No se ha encontrado la película",
                                          "status": 404,
                                          "detail": "No se encontró la película de id 99",
                                          "instance": "/api/v1/peliculas/99"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Ya existe otra película con ese título",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 409",
                                    summary = "Título duplicado",
                                    value = """
                                        {
                                          "type": "https://example.com/errors/conflict",
                                          "title": "Ya existe una película con ese título",
                                          "status": 409,
                                          "detail": "Ya existe una película con el título Batman Begins",
                                          "instance": "/api/v1/peliculas/1"
                                        }
                                        """
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<GetPeliculaDetail> updatePelicula(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePeliculaDto pelicula) {

        return ResponseEntity.ok(
                GetPeliculaDetail.of(peliculaService.updatePelicula(id, pelicula)));

    }

    @Operation(
            summary = "Borrar una película",
            description = "Permite borrar una película a partir de su identificador. "
                    + "La operación es idempotente: si la película no existe, también devuelve 204"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Película borrada (o inexistente)",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePelicula(@PathVariable Long id) {

        peliculaService.deletePelicula(id);
        return ResponseEntity.noContent().build();

    }

}