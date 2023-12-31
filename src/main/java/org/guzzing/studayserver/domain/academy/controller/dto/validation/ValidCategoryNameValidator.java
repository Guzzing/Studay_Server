package org.guzzing.studayserver.domain.academy.controller.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import org.guzzing.studayserver.domain.academy.util.CategoryInfo;

public class ValidCategoryNameValidator implements ConstraintValidator<ValidCategoryName, List<String>> {

    @Override
    public void initialize(ValidCategoryName constraintAnnotation) {
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

    private boolean isValidAreaOfExpertise(String categoryName) {
        return Arrays.stream(CategoryInfo.values())
                .anyMatch(enumValue -> enumValue.getCategoryName().equals(categoryName));
    }
}
