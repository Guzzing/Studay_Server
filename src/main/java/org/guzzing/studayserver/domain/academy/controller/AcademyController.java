package org.guzzing.studayserver.domain.academy.controller;

import jakarta.validation.Valid;
import org.guzzing.studayserver.domain.academy.controller.dto.request.AcademiesByLocationRequest;
import org.guzzing.studayserver.domain.academy.controller.dto.response.AcademiesByLocationResponses;
import org.guzzing.studayserver.domain.academy.controller.dto.response.AcademyGetResponse;
import org.guzzing.studayserver.domain.academy.service.AcademyService;

import org.guzzing.studayserver.domain.academy.service.dto.param.AcademiesByLocationParam;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByLocationResults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AcademiesByLocationResponses> findByLocation(@ModelAttribute @Valid AcademiesByLocationRequest request)  {
        AcademiesByLocationParam academiesByLocationParam = AcademiesByLocationRequest.to(request);
        AcademiesByLocationResults academiesByLocation = academyService.findAcademiesByLocation(academiesByLocationParam);

        return ResponseEntity.status(HttpStatus.OK)
                .body(AcademiesByLocationResponses.from(academiesByLocation));
    }

}
