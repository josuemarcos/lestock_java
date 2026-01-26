package com.example.lestock.validator.stock;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaterialValidator.class)
public @interface ValidMaterial {
    String message() default "Invalid Material";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
