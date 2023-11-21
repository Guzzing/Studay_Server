package org.guzzing.studayserver.domain.child.service;

import org.guzzing.studayserver.domain.dashboard.facade.vo.ChildInfo;

public interface ChildAccessService {

    ChildInfo findChildInfo(final Long childId);

}
