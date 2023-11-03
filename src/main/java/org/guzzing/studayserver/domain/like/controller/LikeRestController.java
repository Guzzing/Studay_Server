package org.guzzing.studayserver.domain.like.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.guzzing.studayserver.domain.auth.memberId.MemberId;
import org.guzzing.studayserver.domain.like.controller.dto.response.LikeGetResponses;
import org.guzzing.studayserver.domain.like.controller.dto.request.LikePostRequest;
import org.guzzing.studayserver.domain.like.controller.dto.response.LikePostResponse;
import org.guzzing.studayserver.domain.like.service.LikeService;
import org.guzzing.studayserver.domain.like.service.dto.LikeResult;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/likes")
public class LikeRestController {

    private final LikeService likeService;

    public LikeRestController(final LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<LikePostResponse> createLike(
            @Validated @RequestBody LikePostRequest request,
            @MemberId Long memberId
    ) {
        LikeResult result = likeService.createLikeOfAcademy(LikePostRequest.to(request, memberId));

        return ResponseEntity
                .status(CREATED)
                .body(LikePostResponse.from(result));
    }

    @DeleteMapping("/{likeId}")
    public ResponseEntity<Void> removeLike(
            @PathVariable Long likeId,
            @MemberId Long memberId
    ) {
        likeService.removeLikeOfAcademy(likeId, memberId);

        return ResponseEntity
                .noContent()
                .build();
    }

}
