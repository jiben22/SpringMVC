package com.openclassrooms.watchlist;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GoodMovieValidator.class)
public @interface GoodMovie {

    String message() default "Si un film a la note de 8, la priorité devrait être au moins M";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}