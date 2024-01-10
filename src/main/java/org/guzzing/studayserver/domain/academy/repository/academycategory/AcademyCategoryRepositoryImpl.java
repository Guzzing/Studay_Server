package org.guzzing.studayserver.domain.academy.repository.academycategory;

import java.util.List;
import org.guzzing.studayserver.domain.academy.model.AcademyCategory;
import org.springframework.stereotype.Repository;

@Repository
public class AcademyCategoryRepositoryImpl implements AcademyCategoryRepository {

    private final AcademyCategoryJpaRepository academyCategoryJpaRepository;

    public AcademyCategoryRepositoryImpl(AcademyCategoryJpaRepository academyCategoryJpaRepository) {
        this.academyCategoryJpaRepository = academyCategoryJpaRepository;
    }

    @Override
    public AcademyCategory save(AcademyCategory academyCategory) {
        return academyCategoryJpaRepository.save(academyCategory);
    }

    @Override
    public List<Long> findCategoryIdsByAcademyId(Long academyId) {
        return academyCategoryJpaRepository.findCategoryIdsByAcademyId(academyId);
    }

    @Override
    public void deleteAll() {
        academyCategoryJpaRepository.deleteAll();
    }
}
