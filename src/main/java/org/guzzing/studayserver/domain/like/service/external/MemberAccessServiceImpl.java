package org.guzzing.studayserver.domain.like.service.external;

import org.springframework.stereotype.Service;

@Service
public class MemberAccessServiceImpl implements
        MemberAccessService {

    @Override
    public boolean existsMember(Long memberId) {
        return false;
    }
}
