package org.guzzing.studayserver.domain.academy.repository.academy;

import jakarta.persistence.EntityNotFoundException;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.global.error.response.ErrorCode;

public interface AcademyRepository  {

    Academy getById(Long academyId);

    Academy save(Academy academy);


}
