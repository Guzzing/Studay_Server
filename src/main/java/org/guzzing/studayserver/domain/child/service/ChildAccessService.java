package org.guzzing.studayserver.domain.child.service;

import org.guzzing.studayserver.domain.child.service.result.AcademyCalendarDetailChildInfo;
import org.guzzing.studayserver.domain.dashboard.facade.vo.ChildInfo;
import java.util.List;

public interface ChildAccessService {

    ChildInfo findChildInfo(final Long childId);

    List<AcademyCalendarDetailChildInfo> getChildImages(final List<Long> childrenIds);

}
