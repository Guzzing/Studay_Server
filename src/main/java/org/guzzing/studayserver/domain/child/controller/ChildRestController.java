package org.guzzing.studayserver.domain.child.controller;

import jakarta.validation.Valid;
import org.guzzing.studayserver.domain.auth.memberId.MemberId;
import org.guzzing.studayserver.domain.child.service.ChildService;
import org.guzzing.studayserver.domain.child.controller.request.ChildCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/children")
public class ChildRestController {
    private final ChildService childService;

    public ChildRestController(ChildService childService) {
        this.childService = childService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Long> create(@MemberId Long memberId, @RequestBody @Valid ChildCreateRequest request) {
        Long createdChildId = childService.create(request.toParam(memberId));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createdChildId);
    }
}
