package org.guzzing.studayserver.domain.academy.controller;

import jakarta.validation.Valid;
import org.guzzing.studayserver.domain.academy.controller.dto.request.AcademiesByLocationRequest;
import org.guzzing.studayserver.domain.academy.controller.dto.request.AcademiesByNameRequest;
import org.guzzing.studayserver.domain.academy.controller.dto.request.AcademyFilterRequest;
import org.guzzing.studayserver.domain.academy.controller.dto.response.AcademiesByLocationResponses;
import org.guzzing.studayserver.domain.academy.controller.dto.response.AcademiesByNameResponses;
import org.guzzing.studayserver.domain.academy.controller.dto.response.AcademyFilterResponses;
import org.guzzing.studayserver.domain.academy.controller.dto.response.AcademyGetResponse;
import org.guzzing.studayserver.domain.academy.service.AcademyService;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByLocationResults;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByNameResults;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyFilterResults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/academies")
public class AcademyController {

    private final AcademyService academyService;

    public AcademyController(AcademyService academyService) {
        this.academyService = academyService;
    }

    @GetMapping(
            path = "/{academyId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AcademyGetResponse> getAcademy(@PathVariable Long academyId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(AcademyGetResponse.from(academyService.getAcademy(academyId)));
    }

    @GetMapping(
            path = "/complexes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AcademiesByLocationResponses> findByLocation(
            @ModelAttribute @Valid AcademiesByLocationRequest request) {
        AcademiesByLocationResults academiesByLocation =
                academyService.findAcademiesByLocation(AcademiesByLocationRequest.to(request));

        return ResponseEntity.status(HttpStatus.OK)
                .body(AcademiesByLocationResponses.from(academiesByLocation));
    }

    @GetMapping(
            path = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AcademiesByNameResponses> findByName(@ModelAttribute @Valid AcademiesByNameRequest request) {
        AcademiesByNameResults academiesByNameResults = academyService.findAcademiesByName(
                AcademiesByNameRequest.to(request));

        return ResponseEntity.status(HttpStatus.OK)
                .body(AcademiesByNameResponses.from(academiesByNameResults));
    }

    @GetMapping(
            path = "/filter",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AcademyFilterResponses> filterAcademies(@ModelAttribute @Valid AcademyFilterRequest request)  {
        AcademyFilterResults academyFilterResults = academyService.filterAcademies(AcademyFilterRequest.to(request));

        return ResponseEntity.status(HttpStatus.OK)
                .body(AcademyFilterResponses.from(academyFilterResults));
    }
}
