package com.salesianos.dam.gestiondepeliculas.validacion;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DirectorMayoriaEdadValidator.class)
@Documented
public @interface DirectorMayoriaEdad {

    String message() default "El director debe ser mayor de edad.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


}
