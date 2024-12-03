package co.inventorsoft.hoteloccupancy.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class GreaterThanZeroValidator implements ConstraintValidator<GreaterThanZero, List<? extends Number>> {

    @Override
    public boolean isValid(List<? extends Number> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        for (var number : value) {
            if (number.doubleValue() < 0) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Value cannot be negative")
                        .addPropertyNode("potentialGuests")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
