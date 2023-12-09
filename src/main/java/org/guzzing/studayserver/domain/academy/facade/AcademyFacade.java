package org.guzzing.studayserver.domain.academy.facade;

import org.guzzing.studayserver.domain.academy.facade.dto.AcademiesByLocationWithScrollFacadeParam;
import org.guzzing.studayserver.domain.academy.facade.dto.AcademiesByLocationWithScrollFacadeResult;
import org.guzzing.studayserver.domain.academy.facade.dto.AcademyDetailFacadeParam;
import org.guzzing.studayserver.domain.academy.facade.dto.AcademyDetailFacadeResult;
import org.guzzing.studayserver.domain.academy.service.AcademyService;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByLocationWithScrollResults;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyGetResult;
import org.guzzing.studayserver.domain.academy.util.GeometryUtil;
import org.guzzing.studayserver.domain.like.service.LikeService;
import org.guzzing.studayserver.domain.region.service.RegionService;
import org.guzzing.studayserver.domain.region.service.dto.location.RegionResult;
import org.springframework.stereotype.Service;

@Service
public class AcademyFacade {

    private final RegionService regionService;
    private final AcademyService academyService;
    private final LikeService likeService;

    public AcademyFacade(RegionService regionService, AcademyService academyService, LikeService likeService) {
        this.regionService = regionService;
        this.academyService = academyService;
        this.likeService = likeService;
    }

    public AcademiesByLocationWithScrollFacadeResult findByLocationWithScroll(AcademiesByLocationWithScrollFacadeParam param) {
        AcademiesByLocationWithScrollResults academiesByLocationWithScroll = academyService.findAcademiesByLocationWithScroll(
                AcademiesByLocationWithScrollFacadeParam.to(param));

        RegionResult regionContainingPoint = regionService.findRegionContainingPoint(
                GeometryUtil.createPoint(
                        param.lat(),
                        param.lng()));

        return AcademiesByLocationWithScrollFacadeResult.from(
                academiesByLocationWithScroll,
                regionContainingPoint);
    }

    public AcademyDetailFacadeResult getDetailAcademy(AcademyDetailFacadeParam param) {
        AcademyGetResult academyGetResult = academyService.getAcademy(param.academyId());
        boolean liked = likeService.isLiked(param.academyId(), param.memberId());

        return AcademyDetailFacadeResult.of(academyGetResult, liked);
    }

}
