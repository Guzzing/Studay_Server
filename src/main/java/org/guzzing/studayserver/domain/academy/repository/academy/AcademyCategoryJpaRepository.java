package org.guzzing.studayserver.domain.academy.repository.academy;

import org.guzzing.studayserver.domain.academy.model.AcademyCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademyCategoryJpaRepository extends JpaRepository<AcademyCategory, Long>, AcademyCategoryRepository {

    AcademyCategory save(AcademyCategory academyCategory);

}
