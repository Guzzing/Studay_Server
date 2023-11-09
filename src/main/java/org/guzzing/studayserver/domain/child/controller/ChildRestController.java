package org.guzzing.studayserver.domain.child.controller;

import jakarta.validation.Valid;
import org.guzzing.studayserver.domain.auth.memberId.MemberId;
import org.guzzing.studayserver.domain.child.controller.request.ChildCreateRequest;
import org.guzzing.studayserver.domain.child.controller.request.ChildModifyRequest;
import org.guzzing.studayserver.domain.child.controller.response.ChildrenFindResponse;
import org.guzzing.studayserver.domain.child.service.ChildService;
import org.guzzing.studayserver.domain.child.service.param.ChildDeleteParam;
import org.guzzing.studayserver.domain.child.service.result.ChildrenFindResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ChildrenFindResponse> findChildren(@MemberId Long memberId) {
        ChildrenFindResult result = childService.findByMemberId(memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ChildrenFindResponse.from(result));
    }

    @DeleteMapping(path = "/{childId}")
    public ResponseEntity<Void> delete(@MemberId Long memberId, @PathVariable Long childId) {
        childService.delete(new ChildDeleteParam(memberId, childId));

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping(path = "/{childId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Long> modify(
            @MemberId Long memberId,
            @PathVariable Long childId,
            @Valid @RequestBody ChildModifyRequest request) {
        Long modifiedChildId = childService.modify(request.toParam(memberId, childId));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(modifiedChildId);
    }
}
