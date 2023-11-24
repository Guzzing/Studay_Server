package org.guzzing.studayserver.domain.calendar.service;

import java.time.LocalDate;
import java.util.List;
import org.guzzing.studayserver.domain.calendar.exception.DateOverlapException;
import org.guzzing.studayserver.domain.calendar.repository.dto.AcademyTimeTemplateDateInfo;
import org.guzzing.studayserver.global.error.response.ErrorCode;

public class DateTimeOverlapChecker {

    private DateTimeOverlapChecker() {
        throw new RuntimeException(ErrorCode.UTIL_NOT_CONSTRUCTOR.getMessage());
    }

    public static void checkOverlap(List<AcademyTimeTemplateDateInfo> academyTimeTemplateDateInfos,
            LocalDate startDateToUpdate,
            LocalDate endDateToUpdate) throws DateOverlapException {
        for (AcademyTimeTemplateDateInfo academyTimeTemplateDateInfo : academyTimeTemplateDateInfos) {
            if (startDateToUpdate.isAfter(academyTimeTemplateDateInfo.getEndDateOfAttendance()) ||
                    endDateToUpdate.isBefore(academyTimeTemplateDateInfo.getStartDateOfAttendance())) {
                continue;
            }

            throw new DateOverlapException(ErrorCode.DATE_TIME_OVERLAP_ERROR, academyTimeTemplateDateInfo.getId());
        }
    }

}
