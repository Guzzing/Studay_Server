package org.guzzing.studayserver.domain.academy.repository.academy;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByFilterWithScroll;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByLocationWithScroll;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByName;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademyFee;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademyFilterCondition;
import org.guzzing.studayserver.global.error.response.ErrorCode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
public class AcademyRepositoryImpl implements AcademyRepository {

    private final AcademyJpaRepository academyJpaRepository;
    private final AcademyQueryRepository academyQueryRepository;

    public AcademyRepositoryImpl(AcademyJpaRepository academyJpaRepository,
            AcademyQueryRepository academyQueryRepository) {
        this.academyJpaRepository = academyJpaRepository;
        this.academyQueryRepository = academyQueryRepository;
    }

    @Override
    public Academy getById(Long academyId) {
        return academyJpaRepository.findById(academyId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }

    @Override
    public Academy save(Academy academy) {
        return academyJpaRepository.save(academy);
    }

    @Override
    public Slice<AcademiesByName> findAcademiesByName(String academyName, Pageable pageable) {
        return academyJpaRepository.findAcademiesByName(academyName, pageable);
    }

    @Override
    public AcademyFee findAcademyFeeInfo(Long academyId) {
        return academyJpaRepository.findAcademyFeeInfo(academyId);
    }

    @Override
    public boolean existsById(Long academyId) {
        return academyJpaRepository.existsById(academyId);
    }

    @Override
    public void deleteAll() {
        academyJpaRepository.deleteAll();
    }

    @Override
    public Optional<Academy> findAcademyById(Long academyId) {
        return academyJpaRepository.findById(academyId);
    }

    @Override
    public AcademiesByLocationWithScroll findAcademiesByLocation(String pointFormat, Long memberId, int pageNumber,
            int pageSize) {
        return academyQueryRepository.findAcademiesByLocation(pointFormat, memberId, pageNumber, pageSize);
    }

    @Override
    public AcademiesByFilterWithScroll filterAcademies(AcademyFilterCondition academyFilterCondition, Long memberId,
            int pageNumber, int pageSize) {
        return academyQueryRepository.filterAcademies(academyFilterCondition, memberId, pageNumber, pageSize);
    }
}
