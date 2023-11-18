package org.guzzing.studayserver.domain.member.service;

public interface MemberAccessService {

    void validateMember(final Long memberId);

    void validateChild(final Long memberId, final Long childId);
}
