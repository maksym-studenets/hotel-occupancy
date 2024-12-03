package co.inventorsoft.hoteloccupancy.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = GreaterThanZeroValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface GreaterThanZero {

    String message() default "Each element must be greater than zero";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}

