package org.guzzing.studayserver.domain.academy.controller.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class ValidAreaOfExpertiseValidator implements ConstraintValidator<ValidAreaOfExpertise, List<String>> {

    @Override
    public void initialize(ValidAreaOfExpertise constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        for (String areaOfExpertise : value) {
            if (!isValidAreaOfExpertise(areaOfExpertise)) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidAreaOfExpertise(String areaOfExpertise) {
        return Arrays.stream(AreaOfExpertise.values())
                .anyMatch(enumValue -> enumValue.getAreaOfExpertise().equals(areaOfExpertise));
    }
}
