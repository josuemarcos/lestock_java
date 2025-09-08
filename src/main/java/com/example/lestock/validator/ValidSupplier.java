package com.example.lestock.validator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SupplierValidator.class)
public @interface ValidSupplier {
    String message() default "Invalid supplier";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
