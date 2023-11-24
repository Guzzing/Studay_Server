package org.guzzing.studayserver.domain.calendar.controller.dto.request.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateStringValidator.class)
public @interface ValidDateString {

    String message() default "잘못된 날짜 형식입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
