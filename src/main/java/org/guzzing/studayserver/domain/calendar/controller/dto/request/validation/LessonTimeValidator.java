package org.guzzing.studayserver.domain.calendar.controller.dto.request.validation;

import jakarta.validation.ConstraintValidator;

import jakarta.validation.ConstraintValidatorContext;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.LessonTime;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LessonTimeValidator implements ConstraintValidator<ValidLessonTime, LessonTime> {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final int MAX_HOUR = 23;
    private static final int MAX_MINUTE = 59;
    private static final int MIN_TIME = 0;

    @Override
    public void initialize(ValidLessonTime constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LessonTime lessonTime, ConstraintValidatorContext context) {
        if (lessonTime == null) {
            return true;
        }

        try {
            LocalTime startTime = LocalTime.parse(lessonTime.getLessonStartTime(), TIME_FORMATTER);
            LocalTime endTime = LocalTime.parse(lessonTime.getLessonEndTime(), TIME_FORMATTER);

            int startHour = startTime.getHour();
            int startMinute = startTime.getMinute();
            int endHour = endTime.getHour();
            int endMinute = endTime.getMinute();

            if (startHour > MAX_HOUR || startMinute > MAX_MINUTE || endHour > MAX_HOUR || endMinute > MAX_MINUTE
                    || startHour < MIN_TIME || startMinute < MIN_TIME || endHour < MIN_TIME || endMinute < MIN_TIME) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        String.format(
                                "시는 %d를 넘어갈 수 없으며 분은 %d를 넘어갈 수 없습니다. 또한 모두 0이거나 양수여야 합니다.",MAX_HOUR,MAX_MINUTE)
                        ).addConstraintViolation();
                return false;
            }

            if (endTime.isBefore(startTime)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("수업 시작 시간은 수업 끝난시간보다 이전일 수 없습니다."
                ).addConstraintViolation();
                return false;
            }

            return true;
        } catch (DateTimeParseException e) {

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("HH:MM 형태를 가지고 있지 않습니다.")
                    .addConstraintViolation();
            return false;
        }

    }

}
