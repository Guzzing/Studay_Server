package org.guzzing.studayserver.domain.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.guzzing.studayserver.domain.review.model.ReviewType.CHEAP_FEE;
import static org.guzzing.studayserver.domain.review.model.ReviewType.GOOD_FACILITY;

import java.util.Map;
import org.guzzing.studayserver.domain.academy.listener.NewReviewListener;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.model.ReviewCount;
import org.guzzing.studayserver.domain.academy.repository.academy.AcademyRepository;
import org.guzzing.studayserver.domain.academy.repository.review.ReviewCountRepository;
import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.member.service.MemberAccessService;
import org.guzzing.studayserver.domain.review.fixture.ReviewFixture;
import org.guzzing.studayserver.domain.review.model.ReviewType;
import org.guzzing.studayserver.domain.review.repository.ReviewRepository;
import org.guzzing.studayserver.domain.review.service.dto.request.ReviewPostParam;
import org.guzzing.studayserver.domain.review.service.dto.response.ReviewPostResult;
import org.guzzing.studayserver.domain.review.service.dto.response.ReviewableResult;
import org.guzzing.studayserver.global.exception.ReviewException;
import org.guzzing.studayserver.testutil.fixture.academy.AcademyFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @MockBean
    private AcademyAccessService academyAccessService;
    @MockBean
    private MemberAccessService memberAccessService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewCountRepository reviewCountRepository;
    @Autowired
    private AcademyRepository academyRepository;
    @Autowired
    private NewReviewListener newReviewListener;

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();
    }

    @Test
    @DisplayName("해당 학원에 리뷰를 남긴 적이 없으면 리뷰를 등록한다.")
    void createReviewOfAcademy_NotReviewYet_RegisterReview() {
        // Given
        Academy academy = AcademyFixture.academySungnam();
        Academy savedAcademy = academyRepository.save(academy);

        ReviewCount reviewCount = ReviewCount.makeDefaultReviewCount(academy);
        reviewCountRepository.save(reviewCount);

        ReviewPostParam param = ReviewFixture.makeReviewPostParam(savedAcademy.getId());
        Map<ReviewType, Boolean> validReviewMap = ReviewFixture.makeValidReviewMap();

        // When
        int beforeKindnessCount = reviewCountRepository.getByAcademyId(param.academyId()).getKindnessCount();
        ReviewPostResult result = reviewService.createReviewOfAcademy(param);
        int afterKindnessCount = reviewCountRepository.getByAcademyId(param.academyId()).getKindnessCount();

        // Then
        assertThat(afterKindnessCount).isEqualTo(beforeKindnessCount + 1);

        assertThat(result).satisfies(entry -> {
            assertThat(entry.memberId()).isEqualTo(ReviewFixture.memberId);
            assertThat(entry.academyId()).isEqualTo(savedAcademy.getId());
            assertThat(entry.cheapFee()).isEqualTo(validReviewMap.get(CHEAP_FEE));
            assertThat(entry.goodFacility()).isEqualTo(validReviewMap.get(GOOD_FACILITY));
        });
    }

    @Test
    @DisplayName("해당 학원에 리뷰를 남겼다면 리뷰 등록에 실패한다.")
    @Transactional
    void createReviewOfAcademy_Reviewed_Fail() {
        // Given
        boolean isValid = true;
        ReviewPostParam param = ReviewFixture.makeReviewPostParam(isValid);

        reviewService.createReviewOfAcademy(param);

        // When & Then
        assertThatThrownBy(() -> reviewService.createReviewOfAcademy(param))
                .isInstanceOf(ReviewException.class)
                .hasMessage("이미 리뷰를 남겼습니다.");
    }

    @Test
    @DisplayName("리뷰를 3 항목 초과로 남겼다면 리뷰 등록에 실패한다.")
    @Transactional
    void createReviewOfAcademy_GreaterThanThreeReivewTypes_Fail() {
        // Given
        boolean isValid = false;
        ReviewPostParam param = ReviewFixture.makeReviewPostParam(isValid);

        // When & Then
        assertThatThrownBy(() -> reviewService.createReviewOfAcademy(param))
                .isInstanceOf(ReviewException.class)
                .hasMessage("리뷰는 3개 까지 가능합니다.");
    }

    @Test
    @DisplayName("해당 학원에 리뷰를 남긴 적 없으면 리뷰 등록 가능하다.")
    @Transactional
    void isReviewableToAcademy_NotExistsReview_Reviewable() {
        // Given
        final Long memberId = 100L;
        final Long academyId = 100L;

        // When
        ReviewableResult result = reviewService.getReviewableToAcademy(memberId, academyId);

        // Then
        assertThat(result.reviewable()).isTrue();
    }

    @Test
    @DisplayName("해당 학원에 리뷰를 남겼다면 리뷰 등록 불가하다.")
    @Transactional
    void isReviewableToAcademy_ExistsReview_NotReviewable() {
        // Given
        boolean isValid = true;
        ReviewPostParam param = ReviewFixture.makeReviewPostParam(isValid);

        reviewService.createReviewOfAcademy(param);

        // When & Then
        ReviewableResult result = reviewService.getReviewableToAcademy(param.memberId(), param.academyId());

        assertThat(result.reviewable()).isFalse();
    }

}
