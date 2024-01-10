package org.guzzing.studayserver.domain.calendar.controller.dto.request.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.LessonTime;

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

            return isValidTimeRange(startTime, endTime, context)
                    && isStartTimeBeforeEndTime(startTime, endTime, context);
        } catch (DateTimeParseException e) {
            handleInvalidTimeFormat(context);
            return false;
        }
    }

    private boolean isValidTimeRange(LocalTime startTime, LocalTime endTime, ConstraintValidatorContext context) {
        if (isTimeOutOfRange(startTime) || isTimeOutOfRange(endTime)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    String.format("시는 %d를 넘어갈 수 없으며 분은 %d를 넘어갈 수 없습니다. 또한 모두 0이거나 양수여야 합니다.", MAX_HOUR, MAX_MINUTE)
            ).addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean isTimeOutOfRange(LocalTime time) {
        return time.getHour() > MAX_HOUR || time.getMinute() > MAX_MINUTE || time.getHour() < MIN_TIME
                || time.getMinute() < MIN_TIME;
    }

    private boolean isStartTimeBeforeEndTime(LocalTime startTime, LocalTime endTime,
            ConstraintValidatorContext context) {
        if (endTime.isBefore(startTime)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("수업 시작 시간은 수업 끝난시간보다 이전일 수 없습니다.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private void handleInvalidTimeFormat(ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("HH:MM 형태를 가지고 있지 않습니다.")
                .addConstraintViolation();
    }
}
