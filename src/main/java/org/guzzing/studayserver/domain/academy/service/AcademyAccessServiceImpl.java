package org.guzzing.studayserver.domain.academy.service;

import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.repository.academy.AcademyRepository;
import org.guzzing.studayserver.domain.like.service.dto.response.AcademyFeeInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AcademyAccessServiceImpl implements
        AcademyAccessService {

    private final AcademyRepository academyRepository;

    public AcademyAccessServiceImpl(AcademyRepository academyRepository) {
        this.academyRepository = academyRepository;
    }

    @Override
    public AcademyFeeInfo findAcademyFeeInfo(Long academyId) {
        Academy academy = academyRepository.getById(academyId);

        return new AcademyFeeInfo(academy.getName(), academy.getMaxEducationFee());
    }

    @Override
    public boolean existsAcademy(Long academyId) {
        return academyRepository.existsById(academyId);
    }

}
