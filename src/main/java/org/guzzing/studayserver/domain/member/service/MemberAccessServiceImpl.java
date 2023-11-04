package org.guzzing.studayserver.domain.member.service;

import org.springframework.stereotype.Service;

@Service
public class MemberAccessServiceImpl implements
        MemberAccessService {

    @Override
    public boolean existsMember(Long memberId) {
        return false;
    }
}
