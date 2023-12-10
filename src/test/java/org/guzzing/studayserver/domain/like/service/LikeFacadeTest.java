package org.guzzing.studayserver.domain.like.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Objects;
import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyFeeInfo;
import org.guzzing.studayserver.domain.like.controller.dto.request.LikePostRequest;
import org.guzzing.studayserver.domain.like.model.Like;
import org.guzzing.studayserver.domain.like.repository.LikeRepository;
import org.guzzing.studayserver.domain.like.service.dto.request.LikePostParam;
import org.guzzing.studayserver.domain.like.service.dto.response.LikeGetResult;
import org.guzzing.studayserver.domain.like.service.dto.response.LikePostResult;
import org.guzzing.studayserver.domain.member.service.MemberAccessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class LikeFacadeTest {

    @Autowired
    private LikeFacade likeFacade;

    @Autowired
    private LikeRepository likeRepository;

    @MockBean
    private AcademyAccessService academyAccessService;
    @MockBean
    private MemberAccessService memberAccessService;

    private final Long memberId = 1L;
    private final Long academyId = 1L;
    private LikePostParam param;

    @BeforeEach
    void setUp() {
        LikePostRequest request = new LikePostRequest(academyId);
        param = LikePostRequest.to(request, memberId);
    }

    @Test
    @DisplayName("학원에 대해서 좋아요를 등록한다.")
    void createLikeOfAcademy_WithMemberId() {
        // Given & When
        LikePostResult result = likeFacade.createLikeOfAcademy(param);

        // Then
        assertThat(result.memberId()).isEqualTo(memberId);
        assertThat(result.academyId()).isEqualTo(academyId);
    }

    @Test
    @DisplayName("학원에 대해 등록한 좋아요를 제거한다.")
    void removeLikeOfAcademy_LikeId_Remove() {
        // Given
        LikePostResult savedLike = likeFacade.createLikeOfAcademy(param);

        // When
        likeFacade.removeLike(savedLike.likeId(), memberId);

        // Then
        boolean result = likeRepository.existsById(savedLike.likeId());

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("학원 아이디로 등록한 좋아요를 제거한다.")
    void deleteLikeOfAcademy_AcademyId_Delete() {
        // Given
        LikePostResult postResult = likeFacade.createLikeOfAcademy(param);

        // When
        likeFacade.deleteLikeOfAcademy(postResult.academyId(), postResult.memberId());

        // Then
        List<Like> likes = likeRepository.findByMemberId(postResult.memberId());
        List<Long> result = likes.stream()
                .map(Like::getAcademyId)
                .filter(id -> Objects.equals(id, postResult.academyId()))
                .toList();

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("내가 좋아요한 모든 학원 비용 정보를 조회한다.")
    void findAllLikesOfMember_MemberId_AcademyInfo() {
        // Given
        given(academyAccessService.findAcademyFeeInfo(any()))
                .willReturn(new AcademyFeeInfo("학원명", 100L));

        LikePostResult savedLike = likeFacade.createLikeOfAcademy(param);

        // When
        LikeGetResult result = likeFacade.getAllLikesOfMember(savedLike.memberId());

        // Then
        assertThat(result.likeAcademyInfos()).isNotEmpty();
        assertThat(result.totalFee()).isEqualTo(100);
    }

    @Test
    @DisplayName("멤버가 해당 학원에 대해서 좋아요를 했는지 여부를 반환한다.")
    void isLiked_MemberAndAcademy_ReturnTrue() {
        // Given
        final Long memberId = 1L;
        final Long academyId = 1L;
        final Like like = Like.of(memberId, academyId);
        likeRepository.save(like);

        // When
        final boolean result = likeFacade.isLiked(memberId, academyId);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("멤버가 해당 학원에 대해서 좋아요를 했는지 여부를 반환한다.")
    void isLiked_MemberAndAcademy_ReturnFalse() {
        // Given
        final Long memberId = 1L;
        final Long academyId = 1L;
        final Like like = Like.of(memberId, academyId);
        likeRepository.save(like);

        // When
        final boolean result = likeFacade.isLiked(memberId, 2L);

        // Then
        assertThat(result).isFalse();
    }

}
