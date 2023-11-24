package org.guzzing.studayserver.domain.child.service;

import org.guzzing.studayserver.domain.child.model.Child;
import org.guzzing.studayserver.domain.child.repository.ChildRepository;
import org.guzzing.studayserver.domain.child.service.result.AcademyCalendarDetailChildInfo;
import org.guzzing.studayserver.domain.dashboard.facade.vo.ChildInfo;
import org.guzzing.studayserver.global.exception.ChildException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ChildAccessServiceImpl implements
        ChildAccessService {

    private final ChildRepository childRepository;

    public ChildAccessServiceImpl(ChildRepository childRepository) {
        this.childRepository = childRepository;
    }

    @Override
    public ChildInfo findChildInfo(Long childId) {
        final Child child = childRepository.findById(childId)
                .orElseThrow(() -> new ChildException("해당하는 아이가 없습니다."));

        return ChildInfo.from(child);
    }

    @Override
    public List<AcademyCalendarDetailChildInfo> getChildImages(List<Long> childrenIds) {
        return null;
    }

}
