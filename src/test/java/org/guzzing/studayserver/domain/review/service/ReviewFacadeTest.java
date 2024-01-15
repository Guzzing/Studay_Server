package org.guzzing.studayserver.domain.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.guzzing.studayserver.domain.review.model.ReviewType.CHEAP_FEE;
import static org.guzzing.studayserver.domain.review.model.ReviewType.GOOD_FACILITY;

import jakarta.persistence.EntityExistsException;
import java.util.Map;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.model.ReviewCount;
import org.guzzing.studayserver.domain.academy.repository.academy.AcademyRepository;
import org.guzzing.studayserver.domain.academy.repository.review.ReviewCountRepository;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.guzzing.studayserver.domain.review.model.ReviewType;
import org.guzzing.studayserver.domain.review.service.dto.request.ReviewPostParam;
import org.guzzing.studayserver.domain.review.service.dto.response.ReviewPostResult;
import org.guzzing.studayserver.domain.review.service.dto.response.ReviewableResult;
import org.guzzing.studayserver.testutil.fixture.academy.AcademyFixture;
import org.guzzing.studayserver.testutil.fixture.member.MemberFixture;
import org.guzzing.studayserver.testutil.fixture.review.ReviewFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ReviewFacadeTest {

    @Autowired
    private ReviewFacade reviewFacade;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AcademyRepository academyRepository;
    @Autowired
    private ReviewCountRepository reviewCountRepository;

    private Member savedMember;
    private Academy savedAcademy;

    @BeforeEach
    void setUp() {
        savedMember = memberRepository.save(MemberFixture.makeMemberEntity());
        savedAcademy = academyRepository.save(AcademyFixture.academySungnam());
        reviewCountRepository.save(ReviewCount.makeDefaultReviewCount(savedAcademy));
    }

    @Test
    @DisplayName("해당 학원에 리뷰를 남긴 적이 없으면 리뷰를 등록한다.")
    void createReviewOfAcademy_NotReviewYet_RegisterReview() {
        // Given
        Map<ReviewType, Boolean> reviewMap = ReviewFixture.makeValidReviewMap();
        ReviewPostParam param = ReviewFixture.makeReviewPostParam(savedMember.getId(), savedAcademy.getId(), reviewMap);

        // When
        ReviewPostResult result = reviewFacade.createReviewOfAcademy(param);
        int kindnessCount = reviewCountRepository.getByAcademyId(param.academyId())
                .getKindnessCount();

        // Then
        assertThat(kindnessCount).isOne();

        assertThat(result).satisfies(entry -> {
            assertThat(entry.memberId()).isEqualTo(savedMember.getId());
            assertThat(entry.academyId()).isEqualTo(savedAcademy.getId());
            assertThat(entry.cheapFee()).isEqualTo(reviewMap.get(CHEAP_FEE));
            assertThat(entry.goodFacility()).isEqualTo(reviewMap.get(GOOD_FACILITY));
        });
    }

    @Test
    @DisplayName("해당 학원에 리뷰를 남겼다면 리뷰 등록에 실패한다.")
    void createReviewOfAcademy_Reviewed_Fail() {
        // Given
        ReviewPostParam param = ReviewFixture.makeReviewPostParam(savedMember.getId(), savedAcademy.getId(),
                ReviewFixture.makeValidReviewMap());

        reviewFacade.createReviewOfAcademy(param);

        // When & Then
        assertThatThrownBy(() -> reviewFacade.createReviewOfAcademy(param))
                .isInstanceOf(EntityExistsException.class)
                .hasMessage("이미 리뷰를 남겼습니다.");
    }

    @Test
    @DisplayName("리뷰를 3 항목 초과로 남겼다면 리뷰 등록에 실패한다.")
    void createReviewOfAcademy_GreaterThanThreeReviewTypes_Fail() {
        // Given
        ReviewPostParam param = ReviewFixture.makeReviewPostParam(savedMember.getId(), savedAcademy.getId(),
                ReviewFixture.makeInvalidReviewMap());

        // When & Then
        assertThatThrownBy(() -> reviewFacade.createReviewOfAcademy(param))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("리뷰는 3개 까지 가능합니다.");
    }

    @Test
    @DisplayName("해당 학원에 리뷰를 남긴 적 없으면 리뷰 등록 가능하다.")
    void isReviewableToAcademy_NotExistsReview_Reviewable() {
        // When
        ReviewableResult result = reviewFacade.getReviewableToAcademy(savedMember.getId(), savedAcademy.getId());

        // Then
        assertThat(result.reviewable()).isTrue();
    }

    @Test
    @DisplayName("해당 학원에 리뷰를 남겼다면 리뷰 등록 불가하다.")
    void isReviewableToAcademy_ExistsReview_NotReviewable() {
        // Given
        ReviewPostParam param = ReviewFixture.makeReviewPostParam(savedMember.getId(), savedAcademy.getId(),
                ReviewFixture.makeValidReviewMap());

        reviewFacade.createReviewOfAcademy(param);

        // When & Then
        ReviewableResult result = reviewFacade.getReviewableToAcademy(param.memberId(), param.academyId());

        assertThat(result.reviewable()).isFalse();
    }

}
