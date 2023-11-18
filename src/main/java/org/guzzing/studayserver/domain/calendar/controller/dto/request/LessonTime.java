package org.guzzing.studayserver.domain.calendar.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.validation.ValidLessonTime;

@Getter
@ValidLessonTime
public class LessonTime {

    private String lessonStartTime;

    private String lessonEndTime;

    @NotBlank
    public LessonTime(String lessonStartTime, String lessonEndTime) {
        this.lessonStartTime = lessonStartTime;
        this.lessonEndTime = lessonEndTime;
    }
}
