package org.guzzing.studayserver.domain.calendar.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.validation.ValidLessonTime;

@ValidLessonTime
public record LessonTime(
        String lessonStartTime,
        String lessonEndTime) {
}
