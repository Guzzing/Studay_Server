package org.guzzing.studayserver.domain.review.controller;

import static org.springframework.http.HttpStatus.CREATED;

import org.guzzing.studayserver.domain.auth.memberId.MemberId;
import org.guzzing.studayserver.domain.review.controller.dto.ReviewPostRequest;
import org.guzzing.studayserver.domain.review.controller.dto.ReviewPostResponse;
import org.guzzing.studayserver.domain.review.service.ReviewService;
import org.guzzing.studayserver.domain.review.service.dto.ReviewPostParam;
import org.guzzing.studayserver.domain.review.service.dto.ReviewPostResult;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/reviews")
public class ReviewRestController {

    private final ReviewService reviewService;

    public ReviewRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewPostResponse> registerReview(
            @Validated @RequestBody ReviewPostRequest request,
            @MemberId Long memberId
    ) {
        ReviewPostParam param = ReviewPostRequest.to(memberId, request);
        ReviewPostResult result = reviewService.createReviewOfAcademy(param);
        ReviewPostResponse response = ReviewPostResponse.from(result);

        return ResponseEntity
                .status(CREATED)
                .body(response);
    }

}
