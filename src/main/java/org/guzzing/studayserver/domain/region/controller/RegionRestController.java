package org.guzzing.studayserver.domain.region.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.guzzing.studayserver.domain.region.controller.dto.RegionResponse;
import org.guzzing.studayserver.domain.region.service.RegionService;
import org.guzzing.studayserver.domain.region.service.dto.SigunguResult;
import org.guzzing.studayserver.domain.region.service.dto.UpmyeondongResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/regions", produces = APPLICATION_JSON_VALUE)
public class RegionRestController {

    private final RegionService regionService;

    public RegionRestController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping(path = "/beopjungdong")
    public ResponseEntity<RegionResponse> getSubRegions(
            @RequestParam(required = false) String sido,
            @RequestParam(required = false) String sigungu
    ) {
        if (sido == null) {
            return getSidoData();
        } else if (sigungu == null) {
            return getSigunguData(sido);
        }
        return getUpmyeondongData(sido, sigungu);
    }

    private ResponseEntity<RegionResponse> getUpmyeondongData(String sido, String sigungu) {
        UpmyeondongResult result = regionService.findUpmyeondongBySidoAndSigungu(sido, sigungu);
        return ResponseEntity
                .status(OK)
                .body(RegionResponse.from(result));
    }

    private ResponseEntity<RegionResponse> getSigunguData(String sido) {
        SigunguResult result = regionService.findSigungusBySido(sido);
        return ResponseEntity
                .status(OK)
                .body(RegionResponse.from(result));
    }

    private ResponseEntity<RegionResponse> getSidoData() {
        SidoResult result = regionService.findSido();
        return ResponseEntity
                .status(OK)
                .body(RegionResponse.from(result));
    }

}
