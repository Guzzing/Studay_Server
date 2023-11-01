package org.guzzing.studay_server.region.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.guzzing.studay_server.region.service.RegionService;
import org.guzzing.studay_server.region.service.SigunguResult;
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
    public ResponseEntity<RegionResponse> getSigungus(
            @RequestParam(required = false) String sido,
            @RequestParam(required = false) String sigungu
    ) {
        SigunguResult result = regionService.findSigungusBySido(sido);
        return ResponseEntity
                .status(OK)
                .body(RegionResponse.from(result));
    }
}
