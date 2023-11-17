package org.guzzing.studayserver.domain.calendar.controller.dto.request.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AttendanceDateValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAttendanceDate {

    String message() default "마지막 등원일이 시작 등원일 이전이거나 둘 사이의 일정이 3년을 넘어갑니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
