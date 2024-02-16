package org.guzzing.studayserver.domain.academy.facade;

import org.guzzing.studayserver.domain.academy.facade.dto.AcademyDetailFacadeParam;
import org.guzzing.studayserver.domain.academy.facade.dto.AcademyDetailFacadeResult;
import org.guzzing.studayserver.domain.academy.service.AcademyService;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyGetResult;
import org.guzzing.studayserver.domain.like.service.LikeFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AcademyFacade {

    private final AcademyService academyService;
    private final LikeFacade likeFacade;

    public AcademyFacade(AcademyService academyService, LikeFacade likeFacade) {
        this.academyService = academyService;
        this.likeFacade = likeFacade;
    }

    @Transactional(readOnly = true)
    public AcademyDetailFacadeResult getDetailAcademy(AcademyDetailFacadeParam param) {
        AcademyGetResult academyGetResult = academyService.getAcademy(param.academyId());
        boolean liked = likeFacade.isLiked(param.memberId(), param.academyId());

        return AcademyDetailFacadeResult.of(academyGetResult, liked);
    }

}
