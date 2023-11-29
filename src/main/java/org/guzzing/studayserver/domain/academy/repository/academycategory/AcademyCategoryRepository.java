package org.guzzing.studayserver.domain.academy.repository.academycategory;

import java.util.List;
import org.guzzing.studayserver.domain.academy.model.AcademyCategory;

public interface AcademyCategoryRepository {

    AcademyCategory save(AcademyCategory academyCategory);

    List<Long> findCategoryIdsByAcademyId(Long academyId);

    void deleteAll();

}
