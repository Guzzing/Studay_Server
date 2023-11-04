package org.guzzing.studayserver.domain.academy.repository.academy;

import org.guzzing.studayserver.domain.academy.model.Academy;

public interface AcademyRepository {

    Academy getById(Long academyId);

    Academy save(Academy academy);


}
