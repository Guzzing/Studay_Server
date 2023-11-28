package org.guzzing.studayserver.domain.academy.facade;

import org.guzzing.studayserver.domain.academy.facade.dto.AcademiesByLocationFacadeParam;
import org.guzzing.studayserver.domain.academy.facade.dto.AcademiesByLocationFacadeResult;
import org.guzzing.studayserver.domain.academy.service.AcademyService;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByLocationResults;
import org.guzzing.studayserver.domain.academy.util.GeometryUtil;
import org.guzzing.studayserver.domain.region.service.RegionService;
import org.guzzing.studayserver.domain.region.service.dto.location.RegionResult;
import org.springframework.stereotype.Service;

@Service
public class AcademyFacade {

    private final RegionService regionService;
    private final AcademyService academyService;

    public AcademyFacade(RegionService regionService, AcademyService academyService) {
        this.regionService = regionService;
        this.academyService = academyService;
    }

    public AcademiesByLocationFacadeResult findByLocation(AcademiesByLocationFacadeParam param) {
        AcademiesByLocationResults academiesByLocation = academyService.findAcademiesByLocation(
                AcademiesByLocationFacadeParam.to(param));

        RegionResult regionContainingPoint = regionService.findRegionContainingPoint(
                GeometryUtil.createPoint(
                        param.lat(),
                        param.lng()));

        return AcademiesByLocationFacadeResult.from(academiesByLocation, regionContainingPoint);
    }

}