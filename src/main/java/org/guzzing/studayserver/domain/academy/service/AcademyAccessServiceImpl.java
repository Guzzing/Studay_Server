package org.guzzing.studayserver.domain.academy.service;

import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.like.service.dto.response.AcademyFeeInfo;
import org.springframework.stereotype.Service;

@Service
public class AcademyAccessServiceImpl implements
        AcademyAccessService {

    @Override
    public AcademyFeeInfo findAcademyFeeInfo(Long academyId) {
        return null;
    }

    @Override
    public boolean existsAcademy(Long academyId) {
        return false;
    }

}
