package com.salesianos.dam.gestiondepeliculas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pelicula {

    @Id @GeneratedValue
    private Long id;

    private String titulo;
    private String genero;
    private LocalDate fechaEstreno;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_pelicula_director"))
    private Director director;


}
