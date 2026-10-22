package com.salesianos.dam.gestiondepeliculas.validacion;

import com.salesianos.dam.gestiondepeliculas.repository.DirectorRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Period;

public class DirectorMayoriaEdadValidator implements ConstraintValidator<DirectorMayoriaEdad, Long> {

    @Autowired
    private DirectorRepository directorRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {

        LocalDate fechaActual = LocalDate.now();

        LocalDate anioNacimiento = LocalDate.of(directorRepository.findById(value)
                .get().getAnioNacimiento(), 1, 1);

        return Period.between(anioNacimiento, fechaActual).getYears() >= 18;
    }

}