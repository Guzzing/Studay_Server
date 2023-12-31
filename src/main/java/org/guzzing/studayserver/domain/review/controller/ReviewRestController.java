package org.guzzing.studayserver.domain.review.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import jakarta.validation.Valid;
import org.guzzing.studayserver.domain.auth.memberId.MemberId;
import org.guzzing.studayserver.domain.review.controller.dto.request.ReviewPostRequest;
import org.guzzing.studayserver.domain.review.controller.dto.response.ReviewPostResponse;
import org.guzzing.studayserver.domain.review.controller.dto.response.ReviewableResponse;
import org.guzzing.studayserver.domain.review.service.ReviewFacade;
import org.guzzing.studayserver.domain.review.service.dto.request.ReviewPostParam;
import org.guzzing.studayserver.domain.review.service.dto.response.ReviewPostResult;
import org.guzzing.studayserver.domain.review.service.dto.response.ReviewableResult;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/reviews")
public class ReviewRestController {

    private final ReviewFacade reviewFacade;

    public ReviewRestController(ReviewFacade reviewFacade) {
        this.reviewFacade = reviewFacade;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewPostResponse> registerReview(
            @MemberId Long memberId,
            @Valid @RequestBody ReviewPostRequest request
    ) {
        ReviewPostParam param = ReviewPostRequest.to(memberId, request);
        ReviewPostResult result = reviewFacade.createReviewOfAcademy(param);
        ReviewPostResponse response = ReviewPostResponse.from(result);

        return ResponseEntity
                .status(CREATED)
                .body(response);
    }

    @GetMapping(path = "/reviewable", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewableResponse> getReviewable(
            @MemberId Long memberId,
            @RequestParam Long academyId
    ) {
        ReviewableResult result = reviewFacade.getReviewableToAcademy(memberId, academyId);
        ReviewableResponse response = ReviewableResponse.from(result);

        return ResponseEntity
                .status(OK)
                .body(response);
    }


}
