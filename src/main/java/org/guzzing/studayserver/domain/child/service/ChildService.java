package org.guzzing.studayserver.domain.child.service;

import org.guzzing.studayserver.domain.child.service.param.ChildCreateParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ChildService {

    public Long create(ChildCreateParam param) {
        return 1L;
    }
}
