package org.guzzing.studayserver.domain.academy.controller.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidAreaOfExpertiseValidator.class)
public @interface ValidAreaOfExpertise {

    String message() default "유효하지 않은 학원 분야입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
