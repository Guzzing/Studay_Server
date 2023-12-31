package org.guzzing.studayserver.domain.like.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import jakarta.validation.Valid;
import org.guzzing.studayserver.domain.auth.member_id.MemberId;
import org.guzzing.studayserver.domain.like.controller.dto.request.LikePostRequest;
import org.guzzing.studayserver.domain.like.controller.dto.response.LikeGetResponses;
import org.guzzing.studayserver.domain.like.controller.dto.response.LikePostResponse;
import org.guzzing.studayserver.domain.like.service.LikeFacade;
import org.guzzing.studayserver.domain.like.service.dto.request.LikePostParam;
import org.guzzing.studayserver.domain.like.service.dto.response.LikeGetResult;
import org.guzzing.studayserver.domain.like.service.dto.response.LikePostResult;
import org.springframework.http.ResponseEntity;
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

    private final LikeFacade likeFacade;

    public LikeRestController(final LikeFacade likeFacade) {
        this.likeFacade = likeFacade;
    }

    /**
     * 좋아요 등록
     *
     * @param request
     * @param memberId
     * @return LikePostResponse
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<LikePostResponse> createLike(
            @Valid @RequestBody final LikePostRequest request,
            @MemberId final Long memberId
    ) {
        final LikePostParam likePostParam = LikePostRequest.to(request, memberId);
        final LikePostResult result = likeFacade.createLikeOfAcademy(likePostParam);

        return ResponseEntity
                .status(CREATED)
                .body(LikePostResponse.from(result));
    }

    /**
     * 좋아요 단건 삭제
     *
     * @param likeId
     * @param memberId
     * @return void
     */
    @DeleteMapping(path = "/{likeId}")
    public ResponseEntity<Void> removeLike(
            @PathVariable final Long likeId,
            @MemberId final Long memberId
    ) {
        likeFacade.removeLike(likeId, memberId);

        return ResponseEntity
                .noContent()
                .build();
    }

    /**
     * 멤버의 좋아요 삭제 - 회원 탈퇴 시 사용
     *
     * @param academyId
     * @param memberId
     * @return void
     */
    @DeleteMapping
    public ResponseEntity<Void> removeLikeOfAcademy(
            @RequestParam final Long academyId,
            @MemberId final Long memberId
    ) {
        likeFacade.deleteLikeOfAcademy(memberId, academyId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<LikeGetResponses> getAllLikes(
            @MemberId final Long memberId
    ) {
        final LikeGetResult allLikedAcademyInfo = likeFacade.getAllLikesOfMember(memberId);

        final LikeGetResponses response = LikeGetResponses.from(allLikedAcademyInfo);

        return ResponseEntity
                .status(OK)
                .body(response);
    }

}
