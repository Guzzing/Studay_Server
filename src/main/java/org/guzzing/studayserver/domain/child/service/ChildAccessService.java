package org.guzzing.studayserver.domain.child.service;

import org.guzzing.studayserver.domain.child.service.result.AcademyCalendarDetailChildInfo;
import org.guzzing.studayserver.domain.dashboard.facade.vo.ChildInfo;

public interface ChildAccessService {

    ChildInfo findChildInfo(final Long childId);

    AcademyCalendarDetailChildInfo getChildImages(final Long childrenId);

}
