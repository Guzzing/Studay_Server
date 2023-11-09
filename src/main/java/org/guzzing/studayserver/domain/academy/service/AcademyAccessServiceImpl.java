package org.guzzing.studayserver.domain.academy.service;

import org.guzzing.studayserver.domain.academy.repository.academy.AcademyRepository;
import org.guzzing.studayserver.domain.like.service.dto.response.AcademyFeeInfo;
import org.springframework.stereotype.Service;

@Service
public class AcademyAccessServiceImpl implements
        AcademyAccessService {

    private final AcademyRepository academyRepository;

    public AcademyAccessServiceImpl(AcademyRepository academyRepository) {
        this.academyRepository = academyRepository;
    }

    @Override
    public AcademyFeeInfo findAcademyFeeInfo(Long academyId) {
        return AcademyFeeInfo.to(academyRepository.findAcademyFeeInfo(academyId));
    }

    @Override
    public boolean existsAcademy(Long academyId) {
        return academyRepository.existsByAcademyId(academyId);
    }

}
