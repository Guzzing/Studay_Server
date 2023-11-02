package org.guzzing.studayserver.domain.academy.controller;

import org.guzzing.studayserver.domain.academy.controller.dto.response.AcademyGetResponse;
import org.guzzing.studayserver.domain.academy.service.AcademyService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
            value = "/{academyId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AcademyGetResponse> getAcademy( @PathVariable Long academyId) {
        return ResponseEntity.ok(
                AcademyGetResponse.of(academyService.getAcademy(academyId))
        );
    }

}
