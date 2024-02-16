package org.guzzing.studayserver.domain.region.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.guzzing.studayserver.domain.region.aop.ValidSido;
import org.guzzing.studayserver.domain.region.aop.ValidSigungu;
import org.guzzing.studayserver.domain.region.aop.ValidUpmyeondong;
import org.guzzing.studayserver.domain.region.controller.dto.RegionGetNameRequest;
import org.guzzing.studayserver.domain.region.controller.dto.RegionGetNameResponse;
import org.guzzing.studayserver.domain.region.controller.dto.RegionLocationResponse;
import org.guzzing.studayserver.domain.region.controller.dto.RegionResponse;
import org.guzzing.studayserver.domain.region.service.RegionService;
import org.guzzing.studayserver.domain.region.service.dto.beopjungdong.SidoResult;
import org.guzzing.studayserver.domain.region.service.dto.beopjungdong.SigunguResult;
import org.guzzing.studayserver.domain.region.service.dto.beopjungdong.UpmyeondongResult;
import org.guzzing.studayserver.domain.region.service.dto.location.RegionGetNameResult;
import org.guzzing.studayserver.domain.region.service.dto.location.RegionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/regions")
public class RegionRestController {

    private final RegionService regionService;

    public RegionRestController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping(path = "/beopjungdong", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RegionResponse> getSido() {
        SidoResult result = regionService.findSido();

        return ResponseEntity
                .status(OK)
                .body(RegionResponse.from(result));

    }

    @ValidSido
    @GetMapping(path = "/beopjungdong/{sido}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RegionResponse> getSigungu(
            @PathVariable String sido
    ) {
        SigunguResult result = regionService.findSigungusBySido(sido);

        return ResponseEntity
                .status(OK)
                .body(RegionResponse.from(result));
    }

    @ValidSido
    @ValidSigungu
    @GetMapping(path = "/beopjungdong/{sido}/{sigungu}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RegionResponse> getUpmyeondong(
            @PathVariable String sido,
            @PathVariable String sigungu
    ) {
        UpmyeondongResult result = regionService.findUpmyeondongBySidoAndSigungu(sido, sigungu);

        return ResponseEntity
                .status(OK)
                .body(RegionResponse.from(result));
    }

    @ValidSido
    @ValidSigungu
    @ValidUpmyeondong
    @GetMapping(path = "/location", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RegionLocationResponse> getLocation(
            @RequestParam String sido,
            @RequestParam String sigungu,
            @RequestParam String upmyeondong
    ) {
        RegionResult regionResult = regionService.findLocation(sido, sigungu, upmyeondong);
        return ResponseEntity
                .status(OK)
                .body(RegionLocationResponse.from(regionResult));
    }

    @GetMapping(
        produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RegionGetNameResponse> getRegionName(
        @ModelAttribute @Valid RegionGetNameRequest request
    ) {
        RegionGetNameResult regionContainingPoint = regionService.getRegionName(
            request.toRegionGetNameParam());

        return ResponseEntity
            .status(OK)
            .body(RegionGetNameResponse.to(regionContainingPoint));
    }

}
