package com.salesianos.dam.gestiondepeliculas.controller;

import com.salesianos.dam.gestiondepeliculas.dto.director.EditDirectorDto;
import com.salesianos.dam.gestiondepeliculas.dto.director.GetDirectorDetail;
import com.salesianos.dam.gestiondepeliculas.model.Director;
import com.salesianos.dam.gestiondepeliculas.service.DirectorService;
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
@RequestMapping("/api/v1/director")
@Tag(name = "Director Controller", description = "Gestión de los directores de las películas")
public class DirectorController {

    private final DirectorService directorService;

    @Operation(
            summary = "Crear un director",
            description = "Permite dar de alta un nuevo director en el catálogo"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Director creado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetDirectorDetail.class),
                            examples = @ExampleObject(
                                    name = "Director creado",
                                    value = """
                                            {
                                              "id": 1,
                                              "name": "Guillermo del Toro",
                                              "anioNacimiento": 1964
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
                                    summary = "Nombre del director vacío",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Bad Request",
                                              "status": 400,
                                              "detail": "Error de validación",
                                              "instance": "/director",
                                              "invalid-params": [
                                                {
                                                  "object": "editDirectorDto",
                                                  "message": "El nombre del director es obligatorio",
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
    public ResponseEntity<GetDirectorDetail> addDirector(
            @Valid @RequestBody EditDirectorDto director) {

        return ResponseEntity.status(201)
                .body(GetDirectorDetail.of(directorService.addDirector(director)));

    }

    @Operation(
            summary = "Obtener todos los directores",
            description = "Permite obtener el listado de todos los directores del catálogo"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de directores",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = GetDirectorDetail.class)
                            ),
                            examples = @ExampleObject(
                                    name = "Listado de directores",
                                    value = """
                                            [
                                              {
                                                "id": 1,
                                                "name": "Guillermo del Toro",
                                                "anioNacimiento": 1964
                                              },
                                              {
                                                "id": 2,
                                                "name": "Christopher Nolan",
                                                "anioNacimiento": 1970
                                              }
                                            ]
                                            """
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<GetDirectorDetail>> getAllDirectores() {

        List<Director> result = directorService.getAllDirectores();
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetDirectorDetail::of)
                        .toList());
    }

    @Operation(
            summary = "Obtener un director por su id",
            description = "Permite obtener los datos de un director a partir de su identificador"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Director encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetDirectorDetail.class),
                            examples = @ExampleObject(
                                    name = "Director encontrado",
                                    value = """
                                            {
                                              "id": 1,
                                              "name": "Guillermo del Toro",
                                              "anioNacimiento": 1964
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
                    description = "No existe un director con ese id",
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
                                              "instance": "/director/99"
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetDirectorDetail> getDirectorById(@PathVariable @Min(value = 1) Long id) {

        return ResponseEntity.ok(GetDirectorDetail.of(directorService.getDirectorById(id)));

    }

    @Operation(
            summary = "Actualizar un director",
            description = "Permite actualizar el nombre y/o el año de nacimiento de un director. "
                    + "Solo se modifican los campos que se envían"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Director actualizado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetDirectorDetail.class),
                            examples = @ExampleObject(
                                    name = "Director actualizado",
                                    value = """
                                            {
                                              "id": 1,
                                              "name": "Guillermo del Toro",
                                              "anioNacimiento": 1964
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un director con ese id",
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
                                              "instance": "/director/99"
                                            }
                                            """
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<GetDirectorDetail> updateDirector(
            @PathVariable Long id,
            @RequestBody EditDirectorDto director) {

        return ResponseEntity.ok(
                GetDirectorDetail.of(directorService.updateDirector(id, director)));


    }

    @Operation(
            summary = "Borrar un director",
            description = "Permite borrar un director a partir de su identificador. "
                    + "La operación es idempotente: si el director no existe, también devuelve 204"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Director borrado (o inexistente)",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable Long id) {

        directorService.deleteDirector(id);
        return ResponseEntity.noContent().build();

    }

}