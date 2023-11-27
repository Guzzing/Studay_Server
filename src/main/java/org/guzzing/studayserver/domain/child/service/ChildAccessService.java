package org.guzzing.studayserver.domain.child.service;

import java.util.List;
import org.guzzing.studayserver.domain.child.service.result.AcademyCalendarDetailChildInfo;
import org.guzzing.studayserver.domain.dashboard.facade.vo.ChildInfo;

public interface ChildAccessService {

    ChildInfo findChildInfo(final Long childId);

    List<AcademyCalendarDetailChildInfo> getChildImages(final List<Long> childrenIds);

}
