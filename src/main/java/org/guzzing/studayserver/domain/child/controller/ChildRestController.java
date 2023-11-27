package org.guzzing.studayserver.domain.child.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import jakarta.validation.Valid;
import org.guzzing.studayserver.domain.auth.memberId.MemberId;
import org.guzzing.studayserver.domain.child.controller.request.ChildCreateRequest;
import org.guzzing.studayserver.domain.child.controller.request.ChildModifyRequest;
import org.guzzing.studayserver.domain.child.controller.response.ChildProfileImagePatchResponse;
import org.guzzing.studayserver.domain.child.controller.response.ChildrenFindResponse;
import org.guzzing.studayserver.domain.child.service.ChildService;
import org.guzzing.studayserver.domain.child.service.param.ChildDeleteParam;
import org.guzzing.studayserver.domain.child.service.result.ChildProfileImagePatchResult;
import org.guzzing.studayserver.domain.child.service.result.ChildrenFindResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/children")
public class ChildRestController {

    private final ChildService childService;

    public ChildRestController(ChildService childService) {
        this.childService = childService;
    }

    @PostMapping(
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Long> create(@MemberId Long memberId, @RequestBody @Valid ChildCreateRequest request) {
        Long createdChildId = childService.create(request.toParam(), memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createdChildId);
    }

    @GetMapping(
            produces = APPLICATION_JSON_VALUE
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
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
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

    @PostMapping(path = "/{childId}/profile", consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ChildProfileImagePatchResponse> modifyProfileImage(
            @PathVariable final Long childId,
            @RequestPart final MultipartFile file
    ) {
        final ChildProfileImagePatchResult result = childService.modifyProfileImage(childId, file);
        final ChildProfileImagePatchResponse response = ChildProfileImagePatchResponse.from(result);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
