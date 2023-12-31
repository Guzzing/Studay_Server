package org.guzzing.studayserver.domain.like.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.guzzing.studayserver.domain.auth.memberId.MemberId;
import org.guzzing.studayserver.domain.like.controller.dto.request.LikePostRequest;
import org.guzzing.studayserver.domain.like.controller.dto.response.LikeGetResponses;
import org.guzzing.studayserver.domain.like.controller.dto.response.LikePostResponse;
import org.guzzing.studayserver.domain.like.service.LikeService;
import org.guzzing.studayserver.domain.like.service.dto.response.LikeGetResult;
import org.guzzing.studayserver.domain.like.service.dto.response.LikePostResult;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<LikePostResponse> createLike(
            @Validated @RequestBody final LikePostRequest request,
            @MemberId final Long memberId
    ) {
        final LikePostResult result = likeService.createLikeOfAcademy(LikePostRequest.to(request, memberId));

        return ResponseEntity
                .status(CREATED)
                .body(LikePostResponse.from(result));
    }

    @DeleteMapping(path = "/{likeId}")
    public ResponseEntity<Void> removeLike(
            @PathVariable final Long likeId,
            @MemberId final Long memberId
    ) {
        likeService.removeLike(likeId, memberId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeLikeOfAcademy(
            @RequestParam final Long academyId,
            @MemberId final Long memberId
    ) {
        likeService.deleteLikeOfAcademy(academyId, memberId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<LikeGetResponses> getAllLikes(
            @MemberId final Long memberId
    ) {
        final LikeGetResult allLikedAcademyInfo = likeService.findAllLikesOfMember(memberId);

        final LikeGetResponses response = LikeGetResponses.from(allLikedAcademyInfo);

        return ResponseEntity
                .status(OK)
                .body(response);
    }

}
