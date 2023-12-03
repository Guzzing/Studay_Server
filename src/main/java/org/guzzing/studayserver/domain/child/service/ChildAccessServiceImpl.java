package org.guzzing.studayserver.domain.child.service;

import org.guzzing.studayserver.domain.child.model.Child;
import org.guzzing.studayserver.domain.child.repository.ChildRepository;
import org.guzzing.studayserver.domain.child.service.result.AcademyCalendarDetailChildInfo;
import org.guzzing.studayserver.domain.dashboard.facade.vo.ChildInfo;
import org.guzzing.studayserver.global.exception.ChildException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return ChildInfo.from(getById(childId));
    }

    @Override
    public AcademyCalendarDetailChildInfo getChildImages(Long childrenId) {
        Child child = this.getById(childrenId);
        return new AcademyCalendarDetailChildInfo(childrenId, child.getNickName(),
                child.getProfileImageURLPath());

    }

    private Child getById(Long childId) {
        return childRepository.findById(childId)
                .orElseThrow(() -> new ChildException("해당하는 아이가 없습니다."));
    }

}
