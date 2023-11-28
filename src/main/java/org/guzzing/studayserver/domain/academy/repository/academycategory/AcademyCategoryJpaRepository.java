package org.guzzing.studayserver.domain.academy.repository.academycategory;

import java.util.List;
import org.guzzing.studayserver.domain.academy.model.AcademyCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AcademyCategoryJpaRepository extends JpaRepository<AcademyCategory, Long>, AcademyCategoryRepository {

    AcademyCategory save(AcademyCategory academyCategory);

    @Query("select ac.categoryId from AcademyCategory as ac where ac.academy.id =:academyId")
    List<Long> findCategoryIdsByAcademyId(@Param("academyId") Long academyId);

    void deleteAll();
}
