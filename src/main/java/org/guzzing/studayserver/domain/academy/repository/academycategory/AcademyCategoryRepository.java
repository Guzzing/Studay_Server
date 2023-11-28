package org.guzzing.studayserver.domain.academy.repository.academycategory;

import org.guzzing.studayserver.domain.academy.model.AcademyCategory;

import java.util.List;

public interface AcademyCategoryRepository {

    AcademyCategory save(AcademyCategory academyCategory);

    List<Long> findCategoryIdsByAcademyId(Long academyId);

    void deleteAll();

}
