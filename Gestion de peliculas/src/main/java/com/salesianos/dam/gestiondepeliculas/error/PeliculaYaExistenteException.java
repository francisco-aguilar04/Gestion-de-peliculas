package com.salesianos.dam.gestiondepeliculas.error;

public class PeliculaYaExistenteException extends RuntimeException {
    public PeliculaYaExistenteException(String message) {
        super(message);
    }
}
