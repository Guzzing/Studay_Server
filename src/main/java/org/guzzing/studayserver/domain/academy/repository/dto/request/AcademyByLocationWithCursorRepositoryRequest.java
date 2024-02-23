package org.guzzing.studayserver.domain.academy.repository.dto;

public record AcademyByLocationWithCursorRepositoryRequest(
    String pointFormat,
    Long memberId,
    Long lastAcademyId
) {
}
