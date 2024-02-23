package org.guzzing.studayserver.domain.academy.repository.dto.request;

public record AcademyByLocationWithCursorRepositoryRequest(
    String pointFormat,
    Long memberId,
    Long lastAcademyId
) {
}
