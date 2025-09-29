package com.example.lestock.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {StockMovementValidator.class})
public @interface ValidStockMovement {
    String message() default "Invalid StockMovement";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
