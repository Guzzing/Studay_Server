package org.guzzing.studayserver.domain.like.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.guzzing.studayserver.domain.auth.memberId.MemberId;
import org.guzzing.studayserver.domain.like.controller.dto.LikeRequest;
import org.guzzing.studayserver.domain.like.controller.dto.LikeResponse;
import org.guzzing.studayserver.domain.like.service.LikeService;
import org.guzzing.studayserver.domain.like.service.dto.LikeResult;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/likes")
public class LikeRestController {

    private final LikeService likeService;

    public LikeRestController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<LikeResponse> createLike(
            @Validated @RequestBody LikeRequest request,
            @MemberId Long memberId
    ) {
        LikeResult result = likeService.createLikeOfAcademy(LikeRequest.to(request, memberId));

        return ResponseEntity
                .status(CREATED)
                .body(LikeResponse.from(result));
    }

}
