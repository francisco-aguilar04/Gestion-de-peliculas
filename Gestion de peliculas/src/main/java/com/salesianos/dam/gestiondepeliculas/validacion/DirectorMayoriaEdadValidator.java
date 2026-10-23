package com.salesianos.dam.gestiondepeliculas.validacion;

import com.salesianos.dam.gestiondepeliculas.model.Director;
import com.salesianos.dam.gestiondepeliculas.repository.DirectorRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

public class DirectorMayoriaEdadValidator implements ConstraintValidator<DirectorMayoriaEdad, Long> {

    @Autowired
    private DirectorRepository directorRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        Optional<Director> director = directorRepository.findById(value);

        if (director.isEmpty() || director.get().getAnioNacimiento() == null) {
            return true;
        }

        LocalDate fechaActual = LocalDate.now();
        LocalDate anioNacimiento = LocalDate.of(director.get().getAnioNacimiento(), 1, 1);

        return Period.between(anioNacimiento, fechaActual).getYears() >= 18;
    }

}